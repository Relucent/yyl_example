package yyl.example.basic.environment;

/**
 * 系统类加载器(AppClassLoader)加载时，会将可加载的类的路径都保存在系统变量(java.class.path)中<br>
 */
public class GetClassPathTest {

	public static void main(String[] args) {

		System.out.println("java.class.path:");
		for (String value : System.getProperty("java.class.path").split(System.getProperty("path.separator"))) {
			System.out.println(value);
		}

		System.out.println("java.home:");
		System.out.println(System.getProperty("java.home"));

	}
}
