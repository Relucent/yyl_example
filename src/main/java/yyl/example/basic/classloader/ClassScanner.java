package yyl.example.basic.classloader;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * class 扫描器
 */
public class ClassScanner {

    public static void main(String[] args) throws URISyntaxException {
        Set<String> collection = new TreeSet<>();
        scan(collection);
        collection.stream().forEach(System.err::println);
        System.out.println(compress(collection));
    }

    /**
     * 输出成类似YAML的格式（便于展示或者传输）
     * @param classNames 类名列表
     * @return 压缩后的字符串
     */
    public static String compress(Collection<String> classNames) {
        ArrayList<String> classNameList = new ArrayList<>(classNames);
        Collections.sort(classNameList);
        List<String> prefixs = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        classNames.stream()//
                .sorted() // 排序
                .filter(name -> !name.endsWith("package-info"))// 过滤掉 package-info
                .map(name -> {
                    StringTokenizer tokenizer = new StringTokenizer(name, ".");
                    int size = tokenizer.countTokens();
                    String[] paths = new String[size];
                    for (int i = 0; i < size; i++) {
                        paths[i] = tokenizer.nextToken();
                    }
                    return paths;
                }).forEach(paths -> {
                    int prefixPos = 0;
                    int namePos = paths.length - 1;
                    String name = paths[namePos];
                    while (prefixPos < prefixs.size() && prefixPos < namePos && prefixs.get(prefixPos).equals(paths[prefixPos])) {
                        prefixPos++;
                    }
                    for (int i = prefixs.size() - 1; i >= prefixPos; i--) {
                        prefixs.remove(i);
                    }
                    for (int i = prefixPos; i < namePos; i++) {
                        String path = paths[i];
                        for (int j = 0; j < prefixs.size(); j++) {
                            builder.append(" ");
                        }
                        builder.append(path).append(":\n");
                        prefixs.add(path);
                    }
                    for (int j = 0; j < prefixs.size(); j++) {
                        builder.append(" ");
                    }
                    builder.append(name).append("\n");
                });
        return builder.toString();
    }

    /**
     * 扫描获得当前应用中的类列表
     * @param collection 收集类名的集合
     */
    public static void scan(Set<String> collection) {
        for (ClassLoader loader = Thread.currentThread().getContextClassLoader(); loader != null; loader = loader.getParent()) {
            if (loader instanceof URLClassLoader) {
                scanURL(((URLClassLoader) loader).getURLs(), collection);
            }
        }
        scanJavaLibrary(collection);
    }

    /**
     * 扫描资源中JAVA类
     * @param urls 资源链接
     * @param collection 收集类名的集合
     */
    private static void scanURL(URL[] urls, Set<String> collection) {
        if (urls == null) {
            return;
        }
        for (URL url : urls) {
            String protocol = url.getProtocol();
            if ("file".equalsIgnoreCase(protocol)) {
                String path = url.getPath();
                if (path.toLowerCase().endsWith(".jar")) {
                    scanJarFile(url, collection);
                } else {
                    try {
                        scanDirectory(new File(url.toURI()), null, collection);
                    } catch (URISyntaxException e) {
                        // ignore
                    }
                }
            } else if ("jar".equalsIgnoreCase(protocol)) {
                scanJarFile(url, collection);
            }
        }
    }

    /**
     * 扫描 JAVA Library 中的类列表
     * @param collection 收集类名的集合
     */
    private static void scanJavaLibrary(Set<String> collection) {
        int version = getJavaMajorVersion();
        if (version >= 9) {
            scanJava9PlusLibrary(collection);
        } else {
            scanJava8Library(collection);
        }
    }

    /**
     * 扫描 JAVA Library 中的类列表（JDK 8）
     * @param collection 收集类名的集合
     */
    private static void scanJava8Library(Set<String> collection) {
        try {
            // 直接反射调用..
            Object classpath = Class.forName("sun.misc.Launcher").getMethod("getBootstrapClassPath").invoke(null);
            scanURL((URL[]) classpath.getClass().getMethod("getURLs").invoke(classpath), collection);
        } catch (Exception e) {
            // ignore
        }
    }

    /**
     * 扫描 JAVA Library 中的类列表（JDK 9+）
     * @param collection 收集类名的集合
     */
    private static void scanJava9PlusLibrary(Set<String> collection) {
        try {
            Class<?> moduleLayer = Class.forName("java.lang.ModuleLayer");
            Object boot = moduleLayer.getMethod("boot").invoke(null);
            Object configuration = moduleLayer.getMethod("configuration").invoke(boot);
            // Set<ResolvedModule>
            Set<?> modules = (Set<?>) Class.forName("java.lang.module.Configuration").getMethod("modules").invoke(configuration);
            Method reference = Class.forName("java.lang.module.ResolvedModule").getMethod("reference");
            Method open = Class.forName("java.lang.module.ModuleReference").getMethod("open");
            Method list = Class.forName("java.lang.module.ModuleReader").getMethod("list");
            modules.forEach(module -> {
            });
            for (Object module : modules) {
                Object ref = reference.invoke(module);
                try (Closeable reader = (Closeable) open.invoke(ref)) {
                    @SuppressWarnings("unchecked")
                    Stream<String> stream = (Stream<String>) list.invoke(reader);
                    stream.filter(ClassScanner::isGeneralClass)
                            .forEach(className -> collection.add(className.substring(0, className.length() - 6).replace("/", ".")));
                } catch (IOException ignored) {
                }
            }
        } catch (Exception e) {
            // ignore
        }
    }

    /**
     * 扫描目录中的类
     * @param directory 目录
     * @param packageName 包名
     * @param collection 收集类名的集合
     */
    private static void scanDirectory(File directory, String packageName, Set<String> collection) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                String name = file.getName();
                if (file.isDirectory()) {
                    scanDirectory(file, packageName == null ? name : packageName + "." + name, collection);
                } else if (name.endsWith(".class") && !name.contains("$")) {
                    collection.add(trimFullName(packageName + "." + name.substring(0, name.length() - 6)));
                }
            }
        }
    }

    /**
     * 扫描JAR包中的类
     * @param url JAR包地址
     * @param collection 类名称列表
     */
    private static void scanJarFile(URL url, Set<String> collection) {
        try (ZipInputStream zis = new ZipInputStream(url.openStream())) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (!entry.getName().contains("META-INF")) {
                    String className = entry.getName();
                    if (isGeneralClass(className)) {
                        collection.add(trimFullName(className.substring(0, className.length() - 6).replace("/", ".")));
                    }
                }
            }
        } catch (IOException e) {
            // ignore
        }
    }

    /**
     * 类名处理：如果是 SpringBoot打包时，要加载的类会多加了2层文件夹 /BOOT-INF/classes/，获得真正的类名需要去这部分
     * @param fullName 类名
     * @return 类名
     */
    private static String trimFullName(String fullName) {
        if (fullName.startsWith("BOOT-INF.classes.")) {
            fullName = fullName.substring(17);
        }
        return fullName;
    }

    /**
     * 判断是否是常规类对象（排除非内部类）
     * @param className 类名
     * @return 否是常规类对象
     */
    private static boolean isGeneralClass(String className) {
        return className.endsWith(".class") && !className.contains("$");
    }

    /**
     * 获得JAVA主版本号
     * @return JAVA主版本号
     */
    private static int getJavaMajorVersion() {
        String version = System.getProperty("java.version");
        int index = version.indexOf(".");
        if (index > -1) {
            // Java 主版本号
            String major = version.substring(0, index);
            // 类似 1.8.0_181形式，取第二位
            if ("1".equals(major)) {
                int endIndex = version.indexOf(".", index + 1);
                major = version.substring(index + 1, endIndex);
            }
            try {
                return new BigDecimal(major).intValue();
            } catch (Exception e) {
                // ignore
            }
        }
        return -1;
    }
}
