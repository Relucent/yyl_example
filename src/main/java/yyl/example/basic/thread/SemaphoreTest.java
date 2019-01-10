package yyl.example.basic.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 计数信号量<br>
 * 它也被更多地用来限制流量，类似阀门的 功能。<br>
 */
public class SemaphoreTest {

    public static void main(String[] args) {
        // 线程池
        ExecutorService exec = Executors.newCachedThreadPool();

        // 只能5个线程同时访问
        final Semaphore semp = new Semaphore(5);

        // 模拟20个客户端访问
        for (int index = 0; index < 20; index++) {
            final int no = index;
            Runnable run = new Runnable() {
                public void run() {
                    try {
                        // 从此信号量获取一个许可，在提供一个许可前一直将线程阻塞，否则线程被中断。获取一个许可并立即返回，将可用的许可数减 1。
                        semp.acquire();
                        try {
                            System.out.println("Accessing: " + no);
                            Thread.sleep((long) (Math.random() * 10000));
                        } finally {
                            // 释放一个许可，将其返回给信号量。释放一个许可，将可用的许可数增加 1。
                            semp.release();
                        }
                    } catch (InterruptedException e) {
                        //
                    }
                }
            };
            exec.execute(run);
        }
        // 退出线程池
        exec.shutdown();
    }
}
