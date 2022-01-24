package yyl.example.demo.oshi;

import java.text.DecimalFormat;

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

    private static final DecimalFormat LOAD_FORMAT = new DecimalFormat("#.00");

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
        int cores = cpu.getLogicalProcessorCount();
        // 系统范围的CPU负载滴答计数器
        long[] previousTicks = cpu.getSystemCpuLoadTicks();
        // 等待一定时间
        sleep(1000L);
        long[] currentTicks = cpu.getSystemCpuLoadTicks();
        // 获取一段时间内的CPU负载标记差

        long user = getTickDifference(previousTicks, currentTicks, TickType.USER);
        long nice = getTickDifference(previousTicks, currentTicks, TickType.NICE);
        long system = getTickDifference(previousTicks, currentTicks, TickType.SYSTEM);
        long idle = getTickDifference(previousTicks, currentTicks, TickType.IDLE);
        long iowait = getTickDifference(previousTicks, currentTicks, TickType.IOWAIT);
        long irq = getTickDifference(previousTicks, currentTicks, TickType.IRQ);
        long softIrq = getTickDifference(previousTicks, currentTicks, TickType.SOFTIRQ);
        long steal = getTickDifference(previousTicks, currentTicks, TickType.STEAL);
        long total = Math.max(user + nice + system + idle + iowait + irq + softIrq + steal, 0);

        println(cpu.toString());// CPU信息
        println("   Cores:   " + cores);// 核心数
        println("   Sys:     " + percentage(system, total));// 系统使用率
        println("   Used:    " + percentage(user, total));// 用户使用率
        println("   Wait:    " + percentage(iowait, total));// 当前等待率
        println("   Free:    " + percentage(idle, total));// 当前空闲率
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

    private static double percentage(long tick, long total) {
        if (0 == total) {
            return 0D;
        }
        // 获取每个CPU核心的tick百分比，需要转成Double计算(tick * 100/ total)
        return Double.parseDouble(LOAD_FORMAT.format(tick <= 0 ? 0 : (tick * 100D / total)));
    }

    private static long getTickDifference(long[] previousTicks, long[] currentTicks, CentralProcessor.TickType tickType) {
        return currentTicks[tickType.getIndex()] - previousTicks[tickType.getIndex()];
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
