package yyl.example.demo.jedis.extend;

import java.util.concurrent.CountDownLatch;

public class JedisDistributedLockExample {

    public static void main(String[] args) throws InterruptedException {

        try (JedisDS ds = JedisDS.builder().setHost("127.0.0.1").setPort(6379).build()) {

            ds.ping();

            JedisDistributedLock lock = ds.getDistributedLock("LOCK01");

            int count = 5;
            CountDownLatch latch = new CountDownLatch(count);

            for (int i = 0; i < count; i++) {
                new Thread(() -> {
                    try {
                        String threadName = Thread.currentThread().getName();
                        System.out.println(threadName + ":lock");
                        lock.lock();
                        System.out.println(threadName + ":lock OK");
                        try {
                            Thread.sleep(1000L);
                        } finally {
                            System.out.println(threadName + ":unlock");
                            lock.unlock();
                            System.out.println(threadName + ":unlock OK");
                            latch.countDown();
                        }
                    } catch (Exception e) {

                    }
                }).start();
            }

            latch.await();
            System.out.println("--------------");
        }
    }
}
