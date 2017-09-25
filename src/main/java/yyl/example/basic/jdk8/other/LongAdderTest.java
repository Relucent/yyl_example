package yyl.example.basic.jdk8.other;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.LongAdder;

/**
 * <h1>并发加法器</h1><br>
 * Java 8另一个出色的功能是并发“加法器”，它对大规模运行的代码尤其有意义。<br>
 * 在Java 8之前，这个问题是用原子类（Atomics）来解决的，它直接利用了CPU的“比较并交换”指令（CAS）来测试并设置计数器的值。<br>
 * 当一条CAS指令因为竞争而失败的时候，AtomicInteger类会死等，在无限循环中不断尝试CAS指令，直到成功为止。<br>
 * 在发生竞争概率很高的环境 中，这种实现被证明是非常慢的。<br>
 * 而 Java 8的LongAdder为大量并行读写数值的代码提供了方便的解决办法。只要初始化一个LongAdder对象并使用它的add()和intValue()方法来累加和采样计数器。<br>
 * 这和旧的Atomic类的区别在于，当CAS指令因为竞争而失败时，Adder不会一直占着CPU，而是为当前线程分配一个内部cell对象来存储 计数器的增量。<br>
 * 然后这个值和其他待处理的cell对象一起被加到intValue()的结果上。 这减少了反复使用CAS指令或阻塞其他线程的可能性。<br>
 */

public class LongAdderTest {
	public static void main(String[] args) throws InterruptedException {

		ExecutorService threadPool = Executors.newCachedThreadPool();

		LongAdder longAdder = new LongAdder();

		for (int i = 0; i < 100; i++) {
			threadPool.execute(() -> longAdder.add(1));
			threadPool.execute(() -> longAdder.add(-1));
		}

		threadPool.shutdown();

		System.out.println(longAdder.intValue());
	}
}
