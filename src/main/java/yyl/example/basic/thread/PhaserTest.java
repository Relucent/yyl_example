package yyl.example.basic.thread;

import java.util.concurrent.Phaser;

/**
 * java7中引入了一种新的可重复使用的同步屏障,称为移相器(Phaser)<br>
 * Phaser比较适合这样一种场景，一种任务可以分为多个阶段，现希望多个线程去处理该批任务，对于每个阶段，多个线程可以并发进行，但是希望保证只有前面一个阶段的任务完成之后才能开始后面的任务。这<br>
 * Phaser主要接口如下：<br>
 * arriveAndAwaitAdvance() 当前线程当前阶段执行完毕，等待其它线程完成当前阶段。如果当前线程是该阶段最后一个未到达的，则该方法直接返回下一个阶段的序号（阶段序号从0开始），同时其它线程的该方法也返回下一个阶段的序号。<br>
 * arriveAndDeregister() 该方法立即返回下一阶段的序号，并且其它线程需要等待的个数减一，并且把当前线程从之后需要等待的成员中移除。如果该Phaser是另外一个Phaser的子Phaser（层次化Phaser会在后文中讲到），并且该操作导致当前Phaser的成员数为0，则该操作也会将当前Phaser从其父Phaser中移除。<br>
 * arrive() 该方法不作任何等待，直接返回下一阶段的序号。<br>
 * awaitAdvance(int phase) 该方法等待某一阶段执行完毕。如果当前阶段不等于指定的阶段或者该Phaser已经被终止，则立即返回。该阶段数一般由arrive()方法或者arriveAndDeregister()方法返回。返回下一阶段的序号，或者返回参数指定的值（如果该参数为负数），或者直接返回当前阶段序号（如果当前Phaser已经被终止）。<br>
 * awaitAdvanceInterruptibly(int phase) 效果与awaitAdvance(int phase)相当，唯一的不同在于若该线程在该方法等待时被中断，则该方法抛出InterruptedException。<br>
 * awaitAdvanceInterruptibly(int phase, long timeout, TimeUnit unit) 效果与awaitAdvanceInterruptibly(int phase)相当，区别在于如果超时则抛出TimeoutException。<br>
 * bulkRegister(int parties) 注册多个party。如果当前phaser已经被终止，则该方法无效，并返回负数。如果调用该方法时，onAdvance方法正在执行，则该方法等待其执行完毕。如果该Phaser有父Phaser则指定的party数大于0，且之前该Phaser的party数为0，那么该Phaser会被注册到其父Phaser中。<br>
 * forceTermination() 强制让该Phaser进入终止状态。已经注册的party数不受影响。如果该Phaser有子Phaser，则其所有的子Phaser均进入终止状态。如果该Phaser已经处于终止状态，该方法调用不造成任何影响。<br>
 */
public class PhaserTest {

	// 多个线程必须等到其它线程的同一阶段的任务全部完成才能进行到下一个阶段，并且每当完成某一阶段任务时，Phaser都会执行其onAdvance方法。
	public static void main(String[] args) {
		int parties = 3;
		int phases = 4;
		final Phaser phaser = new Phaser(parties) {
			@Override
			protected boolean onAdvance(int phase, int registeredParties) {
				System.out.println("====== Phase : " + phase + " ======");
				return registeredParties == 0;
			}
		};
		for (int i = 0; i < parties; i++) {
			final int threadId = i;
			new Thread(() -> {
				for (int phase = 0; phase < phases; phase++) {
					System.out.println(String.format("Thread %s, phase %s", threadId, phase));
					// 当前线程当前阶段执行完毕，等待其它线程完成当前阶段。
					phaser.arriveAndAwaitAdvance();
				}
			}).start();
		}
	}
}
