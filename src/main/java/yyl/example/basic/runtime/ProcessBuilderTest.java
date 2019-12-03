package yyl.example.basic.runtime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import yyl.example.basic.util.IoUtil;

public class ProcessBuilderTest {
	public static void main(String[] args) throws Exception {
		ProcessBuilder processBuilder = new ProcessBuilder();
		processBuilder.redirectErrorStream(true);
		processBuilder.command(new String[] { "cmd", "/C", "ping -n 20 127.1" });
		Process process = processBuilder.start();
		Worker worker = new Worker(process);
		try {
			worker.start();
			worker.join(5000);//最多等待5毫秒
		} catch (InterruptedException e) {
			worker.interrupt();
			throw e;
		} finally {
			process.destroy();
		}

	}

	// ==============================InternalClass========================================

	static class Worker extends Thread {
		private final Process process;

		public Worker(Process process) {
			this.process = process;
			this.setDaemon(true);
		}

		@Override
		public void run() {
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new InputStreamReader(process.getInputStream(), System.getProperty("sun.jnu.encoding")));
				for (String line = null; (line = reader.readLine()) != null;) {
					System.out.println(line);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				IoUtil.closeQuietly(reader);
			}
		}
	}
}
