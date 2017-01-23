package yyl.example.demo.asm;

/*工具类*/

class Util {
	/**
	 * 根据字节码获得类
	 * @param code 字节码
	 * @return 类
	 */
	public static Class<?> defineClass(byte[] code) {
		return CustomClassLoader.INSTANCE.defineClass(code);
	}

	/* 自定义类加载器，将defineClass方法暴露出来 */
	private static final class CustomClassLoader extends ClassLoader {
		static final CustomClassLoader INSTANCE = new CustomClassLoader();

		public Class<?> defineClass(byte[] code) {
			return defineClass(null, code, 0, code.length);
		}
	}

}
