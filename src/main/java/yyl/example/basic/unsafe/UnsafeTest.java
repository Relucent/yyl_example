package yyl.example.basic.unsafe;

import java.lang.reflect.Field;

/**
 * Unsafe <br>
 * 测试JDK1.7
 */
@SuppressWarnings("restriction")
public class UnsafeTest {

	public static void main(String[] args) throws Exception {

		sun.misc.Unsafe unsafe = getUnsafe();

		//获取属性偏移量，可以通过这个偏移量
		Class<T> clazz = T.class;
		Field field = clazz.getField("value");
		long fieldOffset = unsafe.objectFieldOffset(field);
		System.out.println(field.getName() + ":" + fieldOffset);

		T o = new T();

		//更改字段的值 如果原始值是2 改为10
		System.out.println(unsafe.compareAndSwapInt(o, fieldOffset, 1, 10));

		//更改字段的值 如果原始值是1 改为10
		System.out.println(unsafe.compareAndSwapInt(o, fieldOffset, 0, 10));

	}

	static sun.misc.Unsafe getUnsafe() throws Exception {
		Class<?> unsafeClass = Class.forName("sun.misc.Unsafe");
		Field f = unsafeClass.getDeclaredField("theUnsafe");
		f.setAccessible(true);
		return (sun.misc.Unsafe) f.get(null);
	}

	static class T {
		public int value = 0;
	}
}
