package yyl.example.basic.thread;

/**
 * thread.Join把指定的线程加入到当前线程，可以将两个交替执行的线程合并为顺序执行的线程。<br>
 * 比如在线程B中调用了线程A的Join()方法，直到线程A执行完毕后，才会继续执行线程B。<br>
 * 如果join当先线程，那么线程将会一直等待，直到线程中断(interrupt)
 */
public class JoinTest2 {

	//线程永远阻断
	public static void main(String[] args) {
		try {
			//等待当前线程终止，当前线程就不会结束，所以会一直等待下去。除非线程中断(interrupt)
			Thread.currentThread().join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
