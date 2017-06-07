package yyl.example.basic.thread;

/**
 * 线程不安全测试(Worker1可能不会停止)
 */
public class NoThreadSafe2 {

	public static void main(String[] args) throws InterruptedException {
		Worker1 worker1 = new Worker1();
		Worker2 worker2 = new Worker2();
		worker1.start();
		worker2.start();

		Thread.sleep(1000);

		worker1.flag = false;
		worker2.flag = false;

		Thread.sleep(5000);

		System.out.println(worker1.flag);
		System.out.println(worker2.flag);

		System.exit(0);
	}

	static class Worker1 extends Thread {
		boolean flag = true;

		public void run() {
			while (flag) {
			}
			System.out.println("exit worker1");
		};
	}

	static class Worker2 extends Thread {
		volatile boolean flag = true;

		public void run() {
			while (flag) {
			}
			System.out.println("exit worker2");
		};
	}
}
