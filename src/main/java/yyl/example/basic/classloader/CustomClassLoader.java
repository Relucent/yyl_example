package yyl.example.basic.classloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;

/**
 * 自定义类加载器
 */
@SuppressWarnings("rawtypes")
public class CustomClassLoader extends ClassLoader {
    private String basedir; // 需要该类加载器直接加载的类文件的基目录
    private HashSet<String> dynaclazns; // 需要由该类加载器直接加载的类名

    public CustomClassLoader(String basedir, String[] clazns) {
        super(null); // 指定父类加载器为 null
        this.basedir = basedir;
        dynaclazns = new HashSet<String>();
        loadClassByMe(clazns);
    }

    private void loadClassByMe(String[] clazns) {
        for (int i = 0; i < clazns.length; i++) {
            try {
                loadDirectly(clazns[i]);
                dynaclazns.add(clazns[i]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Class loadDirectly(String name) throws FileNotFoundException, IOException {
        StringBuffer sb = new StringBuffer(basedir);
        String classname = name.replace('.', File.separatorChar) + ".class";
        sb.append(File.separator + classname);
        File f = new File(sb.toString());
        InputStream is = new FileInputStream(f);
        byte[] raw = new byte[(int) f.length()];
        is.read(raw);
        is.close();
        return defineClass(name, raw, 0, raw.length);
    }

    @SuppressWarnings("unchecked")
    protected Class loadClass(String name, boolean resolve) throws ClassNotFoundException {
        Class cls = null;
        cls = findLoadedClass(name);
        if (!this.dynaclazns.contains(name) && cls == null) {
            cls = getSystemClassLoader().loadClass(name);
        }
        if (cls == null) {
            throw new ClassNotFoundException(name);
        }
        if (resolve) {
            resolveClass(cls);
        }
        return cls;
    }

}
