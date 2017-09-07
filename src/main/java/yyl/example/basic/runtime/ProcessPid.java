package yyl.example.basic.runtime;

import java.lang.management.ManagementFactory;

/**
 * 获取进程的PID<br>
 * @since 1.5
 * @see org.springframework.boot.ApplicationPid
 */
//RuntimeMXBean Java 虚拟机的运行时系统的管理接口
public class ProcessPid {

	public static void main(String[] args) {
		System.out.println(new ProcessPid().getPid());
	}

	private String getPid() {
		try {
			String jvmName = ManagementFactory.getRuntimeMXBean().getName();
			return jvmName.split("@")[0];
		} catch (Throwable ex) {
			return null;
		}
	}
}
