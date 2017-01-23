package yyl.example.basic.runtime;

public class GetExitCode {

	public static void main(String args[]) {
		try {
			Process p = Runtime.getRuntime().exec("notepad");
			p.waitFor();
			System.out.println(p.exitValue());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}