package yyl.example.demo.jodd;

import jodd.util.SystemInfo;
import jodd.util.SystemUtil;

/**
 * Jodd是一个Java便捷的开源迷你框架，包含工具类、实用功能的集合<br>
 */
public class JoddDemo1 {
    public static void main(String[] args) {
        // 系统信息
        SystemInfo systemInfo = SystemUtil.info();
        System.out.println("CPUs:" + systemInfo.getCPUs());
        System.out.println("MaxMemory:" + systemInfo.getMaxMemory());
        System.out.println("HostAddress:" + systemInfo.getHostAddress());
        System.out.println("JavaVersion:" + systemInfo.getJavaVersion());
        System.out.println("OsName:" + systemInfo.getOsName());
        System.out.println("\n");
    }
}
