package yyl.example.basic.unsafe;

import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * 不安全的对象分配器， 用于在不调用对象构造函数的情况下分配对象。<br>
 * Unsafe是Java中的一个“魔法类”，它是Java虚拟机内部的一个类，用于提供一些低层次的操作。<br>
 * Unsafe类提供了一系列的方法，包括内存操作、线程操作、对象操作等<br>
 * Unsafe可以绕过Java语言的安全机制，直接操作内存，所以如果使用不当，会存在一定的风险，就有可能导致内存泄露、崩溃等问题。<br>
 * 因此在Java在设计时，并没有直接提供给开发者使用它，而只留给了Java虚拟机内部的代码使用。但是在一些特殊场景下，Unsafe可以提供非常强大的功能。<br>
 * 算法参考：com.google.gson.internal.UnsafeAllocator
 */
public abstract class UnsafeAllocator {

    /**
     * 创建一个对象实例
     * @param <T> 实例类型泛型
     * @param type 对象类型
     * @return 对象实例
     * @throws Exception 创建对象失败
     */
    public abstract <T> T newInstance(Class<T> type) throws Exception;

    /**
     * 创建 {@code UnsafeAllocator}
     * @return {@code UnsafeAllocator}实例
     */
    public static UnsafeAllocator create() {
        // try JVM
        // public class Unsafe {
        // public Object allocateInstance(Class<?> type);
        // }
        try {
            Class<?> unsafeClass = Class.forName("sun.misc.Unsafe");
            Field f = unsafeClass.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            final Object unsafe = f.get(null);
            final Method allocateInstance = unsafeClass.getMethod("allocateInstance", Class.class);
            return new UnsafeAllocator() {
                @Override
                @SuppressWarnings("unchecked")
                public <T> T newInstance(Class<T> c) throws Exception {
                    assertInstantiable(c);
                    return (T) allocateInstance.invoke(unsafe, c);
                }
            };
        } catch (Exception ignored) {
        }

        // try dalvikvm, post-gingerbread
        // public class ObjectStreamClass {
        // private static native int getConstructorId(Class<?> c);
        // private static native Object newInstance(Class<?> instantiationClass, int methodId);
        // }
        try {
            Method getConstructorId = ObjectStreamClass.class.getDeclaredMethod("getConstructorId", Class.class);
            getConstructorId.setAccessible(true);
            final int constructorId = (Integer) getConstructorId.invoke(null, Object.class);
            final Method newInstance = ObjectStreamClass.class.getDeclaredMethod("newInstance", Class.class, int.class);
            newInstance.setAccessible(true);
            return new UnsafeAllocator() {
                @Override
                @SuppressWarnings("unchecked")
                public <T> T newInstance(Class<T> c) throws Exception {
                    assertInstantiable(c);
                    return (T) newInstance.invoke(null, c, constructorId);
                }
            };
        } catch (Exception ignored) {
        }

        // try dalvikvm, pre-gingerbread
        // public class ObjectInputStream {
        // private static native Object newInstance(
        // Class<?> instantiationClass, Class<?> constructorClass);
        // }
        try {
            final Method newInstance = ObjectInputStream.class.getDeclaredMethod("newInstance", Class.class, Class.class);
            newInstance.setAccessible(true);
            return new UnsafeAllocator() {
                @Override
                @SuppressWarnings("unchecked")
                public <T> T newInstance(Class<T> c) throws Exception {
                    assertInstantiable(c);
                    return (T) newInstance.invoke(null, c, Object.class);
                }
            };
        } catch (Exception ignored) {
        }

        // give up
        return new UnsafeAllocator() {
            @Override
            public <T> T newInstance(Class<T> c) {
                throw new UnsupportedOperationException("Cannot allocate " + c);
            }
        };
    }

    /**
     * 检查类是否可以由不安全的分配器实例化。<br>
     * 如果类具有接口或抽象修饰符，则抛出{@link java.lang.UnsupportedOperationException}
     * @param type 要检查的类
     */
    private static void assertInstantiable(Class<?> type) {
        int modifiers = type.getModifiers();
        if (Modifier.isInterface(modifiers)) {
            throw new UnsupportedOperationException("Interface can't be instantiated! Interface name: " + type.getName());
        }
        if (Modifier.isAbstract(modifiers)) {
            throw new UnsupportedOperationException("Abstract class can't be instantiated! Class name: " + type.getName());
        }
    }
}
