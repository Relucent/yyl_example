package yyl.example.other.cert;

import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.JTextArea;

public class ExecuteCommand {
	private JTextArea txtDebug;

	protected ExecuteCommand(JTextArea txtDebug) {
		this.txtDebug = txtDebug;
	}

	protected void execute(final String sCommand, final String[] sParams) {
		try {
			txtDebug.append("[ Run: ]	\n");
			txtDebug.append(sCommand);
			final Process process = Runtime.getRuntime().exec(sCommand);

			{
				final InputStream inputStream = process.getInputStream();
				createStartThread(new Runnable() {
					public void run() {
						try {
							byte[] buffer = new byte[1024];
							int count = 0;
							while ((count = inputStream.read(buffer)) > -1) {
								txtDebug.append(new String(buffer, 0, count));
							}
						} catch (Exception e) {

						}
					}
				});
			}
			{
				final InputStream errorStream = process.getErrorStream();
				createStartThread(new Runnable() {
					public void run() {
						try {
							byte[] buffer = new byte[1024];
							int count = 0;
							while ((count = errorStream.read(buffer)) > -1) {
								txtDebug.append(new String(buffer, 0, count));
							}
						} catch (Exception e) {

						}
					}
				});
			}
			{
				final OutputStream outputStream = process.getOutputStream();
				createStartThread(new Runnable() {
					public void run() {
						try {
							for (int i = 0, len = sParams == null ? 0 : sParams.length; i < len; i++) {
								txtDebug.append("\n[Input:]\n" + sParams[i].trim() + "\n");
								outputStream.write((sParams[i].trim() + "\n").getBytes());
								outputStream.flush();
							}
							outputStream.close();
						} catch (Exception e) {

						}
					}
				});
			}
			process.waitFor();
			txtDebug.append("\n");
		} catch (Exception e) {
			txtDebug.append("\n");
			txtDebug.append(e.toString());
		}
	}

	private void createStartThread(Runnable runnable) {
		Thread oThread = new Thread(runnable);
		oThread.setDaemon(true);
		oThread.start();
	}
}
