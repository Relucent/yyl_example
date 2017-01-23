package yyl.example.basic.gc;

/**
 * Java提供finalize()方法，垃圾回收器准备释放内存的时候，会先调用finalize()<br>
 * ※注意：虽然finalize提供了类似析构函数的功能，但是因为各种原因finalize存在风险，所以程序设计时候应当尽量避免!<br>
 */
public class FinalizeTest {

	private static Thread thread = new Thread() {
		private ThreadLocal<Object> local = new ThreadLocal<Object>() {
			@Override
			protected Object initialValue() {
				return new Object() {
					protected void finalize() throws Throwable {
						System.out.println("执行了Finalize!");
					}
				};
			}
		};

		@Override
		public void run() {
			System.out.println(local.get());
		}
	};

	public static void main(String[] args) throws InterruptedException {
		thread.start();
		thread = null;
		Thread.sleep(5000);
		System.gc();
		Thread.sleep(5000);
	}
}
