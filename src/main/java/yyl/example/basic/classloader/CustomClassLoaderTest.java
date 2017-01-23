package yyl.example.basic.classloader;

/**
 * 类加载器测试<br>
 */
@SuppressWarnings("rawtypes")
public class CustomClassLoaderTest {

	private static String LIB_PATH = "target/classes"; // 类的位置
	private static Foliage foliage;

	public static void main(String[] args) {
		final CustomClassLoader cl = new CustomClassLoader(LIB_PATH,
				new String[] { "yyl.example.basic.classloader.Clover" });
		new Thread(new Runnable() {
			public void run() {
				while (true) {
					try {
						Class cls = cl.loadClass("yyl.example.basic.classloader.Clover");
						foliage = (Foliage) cls.newInstance();
						foliage.hello();
						Thread.sleep(1000);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
}
