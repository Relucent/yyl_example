package yyl.example.basic.thread;

/**
 * 生产者消费者模式<br>
 * 自定义的的阻塞队列(这里是为了演示wait/notify的用法，实际上有更好的选择是直接用java.util.concurrent.BlockingQueue)
 */
public class WaitNotifyTest2 {

    public static void main(String[] args) throws InterruptedException {
        final Object lock = new Object();
        final boolean[] turn = { true }; // true = 轮到线程1, false = 轮到线程2
        new Thread() {
            public void run() {
                try {
                    while (true) {
                        synchronized (lock) {
                            while (!turn[0]) {
                                lock.wait();
                            }
                            System.out.println("1");
                            turn[0] = false;
                            lock.notify();// 唤醒线程2
                        }
                    }
                } catch (InterruptedException e) {
                }
            };
        }.start();
        new Thread() {
            public void run() {
                try {
                    while (true) {
                        synchronized (lock) {
                            while (turn[0]) {
                                lock.wait();
                            }
                            System.out.println("2");
                            turn[0] = true;
                            lock.notify(); // 唤醒线程1
                        }
                    }
                } catch (InterruptedException e) {
                }
            };
        }.start();
    }
}
