package yyl.example.basic.runtime;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class CommandWrapper {
	public CommandWrapper(Process process) {
		{
			final InputStream inputStream = process.getInputStream();
			Thread oThread = new Thread() {
				public void run() {
					try {
						byte[] buffer = new byte[1024];
						int count = 0;
						while ((count = inputStream.read(buffer)) > -1) {
							System.out.println(new String(buffer, 0, count));
						}
					} catch (Exception e) {

					}
				}
			};
			oThread.setDaemon(true);
			oThread.start();
		}
		{
			final InputStream errorStream = process.getErrorStream();
			Thread oThread = new Thread() {
				public void run() {
					try {
						byte[] buffer = new byte[1024];
						int count = 0;
						while ((count = errorStream.read(buffer)) > -1) {
							System.out.println(new String(buffer, 0, count));
						}
					} catch (Exception e) {

					}
				}
			};
			oThread.setDaemon(true);
			oThread.start();
		}
		{
			final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			final OutputStream outputStream = process.getOutputStream();
			Thread oThread = new Thread() {
				public void run() {
					try {
						while (true) {
							outputStream.write((reader.readLine() + "\n").getBytes());
							outputStream.flush();
						}
					} catch (Exception e) {

					}
				}
			};
			oThread.setDaemon(true);
			oThread.start();
		}
	}

	public static void main(String[] args) {
		try {
			Process oProcess = Runtime.getRuntime().exec("native2ascii");
			new CommandWrapper(oProcess);
			oProcess.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}