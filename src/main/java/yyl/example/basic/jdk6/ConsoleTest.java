package yyl.example.basic.jdk6;

import java.io.Console;
import java.util.Arrays;

/**
 * java.io.Console的使用 <br>
 * JDK1.6中提供了java.io.Console 类专用来访问基于字符的控制台设备。<br>
 * 你的程序如果要与Windows下的cmd或者Linux下的Terminal交互，就可以用Console类代劳。 <br>
 * 但我们不总是能得到可用的Console，一个JVM是否有可用的Console依赖于底层平台和JVM如何被调用。<br>
 * 如果JVM是在交互式命令行(比如Windows的cmd)中启动的，并且输入输出没有重定向到另外的地方，那么就可以得到一个可用的Console实例。<br>
 * 
 * java.io.Console 只能用在标准输入、输出流未被重定向的原始控制台中使用， 在 Eclipse 或者其他 IDE的控制台无法使用。<br>
 * 编译： <br>
 * javac ConsoleTest.java <br>
 * java ConsoleTest <br>
 */
public class ConsoleTest {

	public static void main(String[] args) {

		Console console = System.console();

		if (console != null) {

			String username = console.readLine("Enter %s : ", "Username");
			System.out.println("Username=" + username);

			char[] password = console.readPassword("Enter Password : ");
			System.out.println("Password=" + Arrays.toString(password));

			console.format("hello %s\n", username);

			System.out.println("Input string");
			String string = console.readLine();
			System.out.println("The input is " + string);

			console.writer().println("finish");
			console.flush();
		} else {
			System.out.println("console==null");
		}
	}
}
