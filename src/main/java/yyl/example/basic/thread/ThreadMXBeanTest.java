package yyl.example.basic.thread;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

/**
 * Java 虚拟机线程系统的管理接口
 */
public class ThreadMXBeanTest {
	public static void main(String[] args) {
		ThreadMXBean bean = ManagementFactory.getThreadMXBean();

		long id = Thread.currentThread().getId();
		//返回指定 ID 的线程的总 CPU 时间
		long tct = bean.getThreadCpuTime(id);
		System.out.println(tct);
	}
}
