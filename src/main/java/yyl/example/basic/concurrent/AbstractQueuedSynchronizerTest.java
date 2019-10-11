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
            int c = getState();
            if (c == 0) {
                if (compareAndSetState(0, acquires)) {
                    setExclusiveOwnerThread(current);
                    return true;
                }
            } else if (current == getExclusiveOwnerThread()) {
                int nextc = c + acquires;
                if (nextc < 0) {// overflow
                    throw new Error("Maximum lock count exceeded");
                }
                setState(nextc);
                return true;
            }
            return false;
        }

        // 试图设置状态来反映独占模式下的一个释放
        @Override
        protected final boolean tryRelease(int releases) {
            int c = getState() - releases;
            if (Thread.currentThread() != getExclusiveOwnerThread()) {
                throw new IllegalMonitorStateException();
            }
            boolean free = false;
            if (c == 0) {
                free = true;
                setExclusiveOwnerThread(null);
            }
            setState(c);
            return free;
        }
    }
}
