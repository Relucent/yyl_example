package yyl.example.basic.unsafe;

import java.lang.reflect.Field;

import io.netty.channel.Channel.Unsafe;

/**
 * Unsafe <br>
 * 测试JDK1.7
 */
public class UnsafeTest {

	private static final sun.misc.Unsafe UNSAFE;
	// 通过反射去获取Unsafe的对象
	static {
		sun.misc.Unsafe unsafe = null;
		try {
			// 通过反射获取rt.jar下的Unsafe类
			Field field = Unsafe.class.getDeclaredField("theUnsafe");
			field.setAccessible(true);
			unsafe = (sun.misc.Unsafe) field.get(null);
		} catch (Exception e) {
			System.err.println("Get Unsafe instance occur error" + e);
		}
		UNSAFE = unsafe;
	}

	public static void main(String[] args) throws Exception {

		// 获取属性偏移量，可以通过这个偏移量
		Class<T> clazz = T.class;
		Field field = clazz.getField("value");
		long fieldOffset = UNSAFE.objectFieldOffset(field);
		System.out.println(field.getName() + ":" + fieldOffset);

		T o = new T();

		// 更改字段的值 如果原始值是2 改为10
		System.out.println(UNSAFE.compareAndSwapInt(o, fieldOffset, 1, 10));

		// 更改字段的值 如果原始值是1 改为10
		System.out.println(UNSAFE.compareAndSwapInt(o, fieldOffset, 0, 10));

	}

	static class T {
		public int value = 0;
	}
}
