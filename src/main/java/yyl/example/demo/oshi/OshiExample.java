package yyl.example.demo.oshi;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.CentralProcessor.TickType;
import oshi.hardware.ComputerSystem;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.VirtualMemory;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;

/**
 * 获得操作系统和硬件信息
 */
public class OshiExample {

    public static void main(String[] args) {
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();
        OperatingSystem os = si.getOperatingSystem();

        infoPlatform(si);
        infoIdentifier(si);
        infoCpu(si);
        infoMemory(hal.getMemory());
        infoFileSystem(os.getFileSystem());
    }

    public static void infoPlatform(SystemInfo si) {
        println("Platform:  " + SystemInfo.getCurrentPlatform());
    }

    public static void infoIdentifier(SystemInfo si) {
        OperatingSystem operatingSystem = si.getOperatingSystem();
        HardwareAbstractionLayer hardwareAbstractionLayer = si.getHardware();
        CentralProcessor centralProcessor = hardwareAbstractionLayer.getProcessor();
        ComputerSystem computerSystem = hardwareAbstractionLayer.getComputerSystem();

        String vendor = operatingSystem.getManufacturer();
        String processorSerialNumber = computerSystem.getSerialNumber();
        String uuid = computerSystem.getHardwareUUID();
        String processorIdentifier = centralProcessor.getProcessorIdentifier().getIdentifier();
        int processors = centralProcessor.getLogicalProcessorCount();

        println(String.format("%08x", vendor.hashCode()) + "-"//
                + String.format("%08x", processorSerialNumber.hashCode()) + "-"//
                + String.format("%08x", uuid.hashCode()) + "-"//
                + String.format("%08x", processorIdentifier.hashCode()) + "-" //
                + processors);
    }

    private static void infoCpu(SystemInfo si) {
        println("CPU");
        CentralProcessor cpu = si.getHardware().getProcessor();
        long[] prevTicks = cpu.getSystemCpuLoadTicks();
        sleep(1000L);
        long[] ticks = cpu.getSystemCpuLoadTicks();
        long nice = ticks[TickType.NICE.getIndex()] - prevTicks[TickType.NICE.getIndex()];
        long irq = ticks[TickType.IRQ.getIndex()] - prevTicks[TickType.IRQ.getIndex()];
        long softirq = ticks[TickType.SOFTIRQ.getIndex()] - prevTicks[TickType.SOFTIRQ.getIndex()];
        long steal = ticks[TickType.STEAL.getIndex()] - prevTicks[TickType.STEAL.getIndex()];
        long system = ticks[TickType.SYSTEM.getIndex()] - prevTicks[TickType.SYSTEM.getIndex()];
        long user = ticks[TickType.USER.getIndex()] - prevTicks[TickType.USER.getIndex()];
        long iowait = ticks[TickType.IOWAIT.getIndex()] - prevTicks[TickType.IOWAIT.getIndex()];
        long idle = ticks[TickType.IDLE.getIndex()] - prevTicks[TickType.IDLE.getIndex()];
        long totalCpu = user + nice + system + idle + iowait + irq + softirq + steal;
        println("   CpuNum:     " + cpu.getLogicalProcessorCount());// 核心数
        println("   Total:      " + totalCpu);// CPU总的使用率
        println("   System:     " + system);// CPU系统使用率
        println("   User:       " + user);// CPU用户使用率
        println("   Wait:       " + iowait);// CPU当前等待率
        println("   Free:       " + idle);// CPU当前空闲率
    }

    private static void infoMemory(GlobalMemory memory) {

        println("Memory");

        long total = memory.getTotal();
        long free = memory.getAvailable();
        long used = total - free;
        println("  total:        " + total);// 内存总量
        println("  used:         " + used);// 已用内存
        println("  free:    " + free);// 剩余内存

        VirtualMemory virtualMemory = memory.getVirtualMemory();

        long swapTotal = virtualMemory.getSwapTotal();
        long swapUsed = virtualMemory.getSwapUsed();
        long swapFree = swapTotal - swapUsed;

        println("  swapTotal:   " + swapTotal);
        println("  swapUsed:    " + swapUsed);
        println("  swapFree:    " + swapFree);
    }

    private static void infoFileSystem(FileSystem fs) {
        println("FileStore");
        for (OSFileStore store : fs.getFileStores()) {
            String name = store.getName();
            String mount = store.getMount();
            String type = store.getType();
            long total = store.getTotalSpace();
            long free = store.getUsableSpace();
            long used = total - free;
            double usage = ((double) used * 100) / total;
            println("   name:   " + name);// 文件系统名
            println("   mount:   " + mount);// 挂载路径
            println("   type:   " + type);// 盘符类型
            println("   total:  " + total);// 总大小
            println("   free:   " + free);// 剩余大小
            println("   used:   " + used);// 已经使用量
            println("   usage:   " + usage);// 资源的使用率
        }
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static void println(Object msg) {
        System.out.println(msg);
    }
}
