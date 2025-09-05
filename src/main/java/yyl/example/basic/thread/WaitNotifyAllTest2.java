package yyl.example.basic.thread;

/**
 * 演示wait/notifyAll的用法，顺序执行
 */
public class WaitNotifyAllTest2 {

    public static void main(String[] args) throws InterruptedException {
        final Object lock = new Object(); // 共享锁
        final int threadCount = 3; // 线程数量
        final int[] turn = { 0 }; // 使用数组包装共享变量 turn
        for (int i = 0; i < threadCount; i++) {
            final int myId = i;
            new Thread(() -> {
                try {
                    while (true) {
                        synchronized (lock) {

                            // 等待轮到自己执行
                            // 虚假唤醒或其它线程 notify 也会让线程醒来，所以必须用 while 重新检查条件
                            while (turn[0] % threadCount != myId) {
                                lock.wait();
                            }

                            // 打印当前线程编号（1, 2, 3）
                            System.out.println(myId + 1);

                            // 更新轮到下一个线程
                            turn[0]++;

                            // 唤醒所有等待线程
                            // 必须 notifyAll() 而不能只用 notify()
                            // 原因：
                            // 1. 多线程轮流执行，每个线程都有条件判断，谁被唤醒是随机的
                            // 2. notify() 只唤醒一个线程，如果唤醒的不是轮到的线程，它会重新 wait，可能所有线程都在 wait 队列 → 死锁
                            // 3. notifyAll() 唤醒所有线程，让每个线程重新检查条件，只有轮到的线程才能继续执行，保证顺序不会被打乱
                            lock.notifyAll(); // 唤醒所有线程
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
