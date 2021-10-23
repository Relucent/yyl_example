package yyl.example.demo.spring.util;

import org.springframework.util.StopWatch;

/**
 * 用来记录任务的执行耗时
 */
public class StopWatchExample {
    public static void main(String[] args) {
        StopWatch sw = new StopWatch();
        try {
            for (int i = 0; i < 5; i++) {
                sw.start("task" + i);
                Thread.sleep(1 * 1000L);
                sw.stop();
            }
        } catch (InterruptedException e) {
            return;
        }
        System.out.println("Total   " + sw.getTotalTimeMillis() + "ms");
        for (StopWatch.TaskInfo taskInfo : sw.getTaskInfo()) {
            System.out.println(taskInfo.getTaskName() + "   " + taskInfo.getTimeMillis() + "ms");
        }
    }
}
