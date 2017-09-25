package yyl.example.basic.jdk8.other;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.concurrent.TimeUnit;

/**
 * 通过JAVA可以启动启动一个操作系统进程(底层通过JNI调用完成)，但是以前版本的JAVA运行进程有一个问题就是进程一旦启动就很难去控制它。<br>
 * 这种情况，有可能进程没有停止而变成僵尸进程。<br>
 * 为了解决这个问题，Java 8在Process类中引入了三个新的方法<br>
 * destroyForcibly：结束一个进程。<br>
 * isAlive：查询你启动的进程是否还活着。<br>
 * waitFor(重载)：可以指定等待进程结束的时间。进程成功退出后这个接口会返回，超时的话也会返回(因为有可能要手动终止它)<br>
 **/
public class ProcessTest {

	public static void main(String[] args) throws Exception {

		//Window环境 ping命令
		Process process = Runtime.getRuntime().exec("cmd /c ping 127.0.0.1 -t ");

		try (InputStream input = process.getInputStream()) {

			new Thread(() -> {
				try {
					Reader reader = new InputStreamReader(input, System.getProperty("sun.jnu.encoding"));
					int c = 0;
					while ((c = reader.read()) != -1) {
						System.out.print((char) c);
					}
				} catch (IOException e) {
					System.err.println(e);
				}
			}).start();

			if (process.waitFor(5000, TimeUnit.MILLISECONDS)) {
				System.out.println("success");
			}
			System.out.println("process.isAlive():" + process.isAlive());
			if (process.isAlive()) {
				input.close();
				process.destroyForcibly();
			}

			System.out.println("process.isAlive():" + process.isAlive());

		}
	}
}
