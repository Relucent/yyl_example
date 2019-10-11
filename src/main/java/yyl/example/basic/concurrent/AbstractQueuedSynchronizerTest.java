package yyl.example.basic.concurrent;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.ReentrantLock;

/**
 * {@code java.util.concurrent.locks.AbstractQueuedSynchronizer} 队列同步器<br>
 * java.util.concurrent包中很多类都依赖于这个类所提供的队列式的同步器，比如说常用的ReentranLock,Semaphore和CountDownLatch等<br>
 * @see AbstractQueuedSynchronizer
 * @see ReentrantLock
 */
public class AbstractQueuedSynchronizerTest {
    public static void main(String[] args) {
        Sync sync = new Sync();
        Runnable runnable = new Runnable() {
            private int k = 0;

            @Override
            public void run() {
                // #lock
                sync.acquire(1);
                try {
                    k++;
                    System.out.println(k);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    // #unlock
                    sync.release(1);
                }
            }
        };
        for (int i = 0; i < 10; i++) {
            new Thread(runnable).start();
        }
    }

    @SuppressWarnings("serial")
    private static class Sync extends AbstractQueuedSynchronizer {

        // 试图在独占模式下获取对象状态
        @Override
        protected boolean tryAcquire(int acquires) {
            final Thread current = Thread.currentThread();

            // 获得变量state
            int c = getState();

            // 判断锁是否被占用，0代表未被占用
            if (c == 0) {
                // 断队列中是否有其他线程在等待
                if (hasQueuedPredecessors()) {
                    return false;
                }
                // 设置锁的状态为1 (被占用)
                if (compareAndSetState(0, acquires)) {
                    // 设置占用排它锁的线程是当前线程
                    setExclusiveOwnerThread(current);
                    return true;
                }
            }
            // 当前锁已被占用，但是占用锁的是当前线程本身
            else if (current == getExclusiveOwnerThread()) {
                // 当已经获取锁的线程每多获取一次锁，state就执行加1操作 (重入的实现)
                int nextc = c + acquires;
                // 超过最大锁定计数(Integer.MAX_VALUE)，溢出了
                if (nextc < 0) {// overflow
                    throw new Error("Maximum lock count exceeded");
                }
                // 当占用锁的是当前线程本身，所以不需要CAS，直接设置状态值即可
                setState(nextc);
                return true;
            }
            return false;
        }

        // 试图设置状态来反映独占模式下的一个释放
        @Override
        protected final boolean tryRelease(int releases) {
            // state就执行减1操作 (重入的实现)
            int c = getState() - releases;
            // 如果占用锁的线程不是当前线程，抛出异常(这种情况一般是代码写的有问题)
            if (Thread.currentThread() != getExclusiveOwnerThread()) {
                throw new IllegalMonitorStateException();
            }

            // 此对象现在处于完全释放状态(其他等待的线程都可以试图获得锁)
            boolean free = false;

            // 0代表未被占用
            if (c == 0) {
                // 完全释放状态为TRUE
                free = true;
                // 设置占用排它锁的线程是NULL
                setExclusiveOwnerThread(null);
            }
            setState(c);

            // 返回完全释放状态状态
            return free;
        }
    }
}
