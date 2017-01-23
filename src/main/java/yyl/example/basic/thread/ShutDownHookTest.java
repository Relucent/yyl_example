package yyl.example.basic.thread;

import java.util.Scanner;

/**
 * Runtime.getRuntime().addShutdownHook(shutdownHook) 的使用<br>
 * 当JVM关闭的时候，会执行系统中已经设置的所有通过方法addShutdownHook添加的钩子，当系统执行完这些钩子后，JVM才会关闭。<br>
 * 所以这些钩子可以在JVM关闭的时候进行内存清理、对象销毁等操作。<br>
 * 这个关闭的钩子可以在以下几个场景被调用<br>
 * 1）程序正常退出<br>
 * 2）使用System.exit()<br>
 * 3）终端使用ctrl+c触发的中断<br>
 * 4）OutofMemory宕机<br>
 * 5）操作系统关闭<br>
 * 6）使用Kill pid命令干掉进程<br>
 * 这样我们可以利用这个特性做到：<br>
 * 1.应用程序正常退出，在退出时执行特定的业务逻辑，或者关闭资源等操作。 <br>
 * 2.虚拟机非正常退出(用户按下ctrl+c、OutofMemory宕机、操作系统关闭),在退出时执行必要的挽救措施。<br>
 * ※重要备注：<br>
 * 如果使用 kill -9 pid 命令干掉进程时候，这个JVM注册的钩子不会被调用!
 */
public class ShutDownHookTest {

	public static void main(String[] args) {

		// 注册一个关闭钩子
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				System.out.println("hook shutdown!");
			}
		});

		Scanner scanner = new Scanner(System.in);
		System.out.println("输入exit退出程序:");

		while (true) {
			String line = scanner.nextLine();
			if ("exit".equals(line)) {
				scanner.close();
				System.exit(0);
			}
			System.out.println(line);
		}
	}

}
