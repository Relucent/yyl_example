package yyl.example.basic.runtime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * jps(Java Virtual Machine Process Status Tool) JAVA虚拟机进程状态工具。<br>
 * 通过 jps 命令可以方便地查看 Java 进程的启动类、传入参数和 Java 虚拟机参数等信息。<br>
 * 
 * <pre>
 * jps -help
 * usage: jps [-help]
 *        jps [-q] [-mlvV] [(hostid)]
 * Definitions:
 *     (hostid):      (hostname)[:<port>]

 * 参数说明
 * -q：只输出进程 ID
 * -m：输出传入 main 方法的参数
 * -l：输出完全的包名，应用主类名，jar的完全路径名
 * -v：输出jvm参数
 * -V：输出通过flag文件传递到JVM中的参数
 * </pre>
 */
public class JpsTest {
	public static void main(String[] args) throws IOException {
		Process process = Runtime.getRuntime().exec("jps -mlvV");
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), "GBK"))) {
			for (String line = reader.readLine(); line != null; line = reader.readLine()) {
				System.out.println(line);
			}
		}
	}
}
