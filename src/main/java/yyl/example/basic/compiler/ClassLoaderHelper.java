package yyl.example.basic.compiler;

/**
 * 类加载工具类
 */
public class ClassLoaderHelper {

    /**
     * 将字节数组转换为类的实例
     * @param code 字节码
     * @return 类的实例对象
     */
    public static Class<?> defineClass(byte[] code) {
        return ClassDefiner.INSTANCE.defineClass(null, code);
    }

    /**
     * 将字节数组转换为类的实例
     * @param name 类名称
     * @param code 构成类数据的字节码
     * @return 类的实例对象
     */
    public static Class<?> defineClass(String name, byte[] code) {
        return ClassDefiner.INSTANCE.defineClass(name, code);
    }

    /** 自定义类加载器，将defineClass方法暴露出来 */
    private static final class ClassDefiner extends ClassLoader {

        /** 单例模式 */
        private static final ClassDefiner INSTANCE = new ClassDefiner();

        /**
         * 将字节数组转换为类的实例
         * @param name 类名称
         * @param code 构成类数据的字节码
         * @return 类的实例对象
         */
        public Class<?> defineClass(String name, byte[] code) {
            return defineClass(name, code, 0, code.length);
        }
    }
}
