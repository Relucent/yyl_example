package yyl.example.demo.jedis.extend;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.function.Consumer;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 基于Redis(Jedis)的分布式锁实现
 */
public class JedisDistributedLock implements Lock {

    private static final String LOCK_KEY_PREFIX = "_yyl__lock:";
    private static final String UNLOCK_MESSAGE = "~unlock";
    private static final String PREFIX_ENTRY = UUID.randomUUID().toString();
    private static final Timer TIMER = new Timer(true);

    private final JedisPool pool;
    private final long internalLockLeaseTime = 30 * 1000L;
    private final Semaphore latch;
    private final String name;
    private final String lockKey;
    private final JedisPubSubs pubSubs;;

    /**
     * 构造函数
     * @param pool Jedis资源池
     * @param pubSubs 发布订阅处理器
     * @param name 锁名称
     */
    public JedisDistributedLock(JedisPool pool, JedisPubSubs pubSubs, String name) {
        this.latch = new Semaphore(0);
        this.pool = pool;
        this.pubSubs = pubSubs;
        this.name = name;
        this.lockKey = LOCK_KEY_PREFIX + ":" + name;
    }

    /**
     * 持有锁， 该方法忽略线程中断，未获取锁之前会一直阻塞
     */
    @Override
    public void lock() {
        try {
            lock(false);
        } catch (InterruptedException e) {
            throw new IllegalStateException();
        }
    }

    /**
     * 持有锁
     * @throws InterruptedException 发生中断异常
     */
    @Override
    public void lockInterruptibly() throws InterruptedException {
        lock(true);
    }

    /**
     * 持有锁
     * @param interruptibly 是否允许中断异常
     * @throws InterruptedException 发生中断异常
     */
    private void lock(boolean interruptibly) throws InterruptedException {
        long threadId = Thread.currentThread().getId();

        Long ttl = tryAcquireCommand(threadId);
        // 已获得锁
        if (ttl == null) {
            return;
        }

        // 解锁消息处理
        Consumer<String> listener = (message) -> {
            if (UNLOCK_MESSAGE.equals(message)) {
                latch.release();
            }
        };

        // 订阅解锁消息
        pubSubs.subscribe(name, listener);

        try {
            while (true) {
                ttl = tryAcquireCommand(threadId);
                // 已获得锁
                if (ttl == null) {
                    break;
                }
                // 设置等待
                if (ttl >= 0) {
                    try {
                        latch.tryAcquire(ttl, TimeUnit.MILLISECONDS);
                    } catch (InterruptedException e) {
                        if (interruptibly) {
                            throw e;
                        }
                        latch.tryAcquire(ttl, TimeUnit.MILLISECONDS);
                    }
                } else {
                    if (interruptibly) {
                        latch.acquire();
                    } else {
                        latch.acquireUninterruptibly();
                    }
                }
            }
        } finally {
            pubSubs.unsubscribe(name, listener);
        }
    }

    /**
     * 仅当锁在调用时是空闲的时才获取锁
     * @return 如果获取了锁返回{@code true}，否则返回{@code false}
     */
    @Override
    public boolean tryLock() {
        return tryAcquireCommand(Thread.currentThread().getId()) == null;
    }

    /**
     * 如果锁在给定的等待时间内空闲，则获取锁；如果在等待时间发生中断，则抛出中断异常
     * @param waitTime 等待时间
     * @param unit 时间单位
     * @return 如果获取了锁返回{@code true}，否则返回{@code false}
     * @throws InterruptedException 发生中断
     */
    @Override
    public boolean tryLock(long waitTime, TimeUnit unit) throws InterruptedException {
        long time = unit.toMillis(waitTime);
        long current = System.currentTimeMillis();
        long threadId = Thread.currentThread().getId();

        Long ttl = tryAcquireCommand(threadId);
        // 获得锁
        if (ttl == null) {
            return true;
        }

        time -= System.currentTimeMillis() - current;
        if (time <= 0) {
            return false;
        }

        current = System.currentTimeMillis();

        // 解锁消息处理
        Consumer<String> listener = (message) -> {
            latch.release();
        };
        // 订阅解锁消息
        pubSubs.subscribe(name, listener);

        try {
            time -= System.currentTimeMillis() - current;
            if (time <= 0) {
                return false;
            }

            while (true) {
                long currentTime = System.currentTimeMillis();
                ttl = tryAcquireCommand(threadId);

                // 获得锁
                if (ttl == null) {
                    return true;
                }

                time -= System.currentTimeMillis() - currentTime;
                if (time <= 0) {
                    return false;
                }

                // 等待解锁消息
                currentTime = System.currentTimeMillis();
                if (0 <= ttl && ttl < time) {
                    latch.tryAcquire(ttl, TimeUnit.MILLISECONDS);
                } else {
                    latch.tryAcquire(time, TimeUnit.MILLISECONDS);
                }

                time -= System.currentTimeMillis() - currentTime;
                if (time <= 0) {
                    return false;
                }
            }
        } finally {
            // 取消消息订阅
            pubSubs.unsubscribe(name, listener);
        }
    }

    /**
     * 释放锁
     */
    @Override
    public void unlock() {
        long threadId = Thread.currentThread().getId();
        tryReleaseCommand(threadId);
    }

    /**
     * 返回一个新的{@link Condition}实例，该实例绑定到此{@code Lock}实例
     * @return 新的{@link Condition}实例
     * @throws UnsupportedOperationException 锁不支持该方法
     * @deprecated 该方法不被支持，调用会抛出 {@code UnsupportedOperationException}
     */
    @Override
    public Condition newCondition() {
        throw new UnsupportedOperationException();
    }

    /**
     * 到期续订
     * @param threadId 线程ID
     * @return 是否续订成功
     */
    protected void renewExpiration(long threadId) {
        TIMER.schedule(new TimerTask() {
            @Override
            public void run() {
                boolean status = renewExpirationCommand(threadId);
                if (status) {
                    renewExpiration(threadId);// 重新安排自己
                }
            }
        }, internalLockLeaseTime / 3);
    }

    /**
     * 尝试获取锁
     * @param threadId 线程ID
     * @return 键剩余过期时间
     */
    private Long tryAcquireCommand(long threadId) {
        try (Jedis jedis = pool.getResource()) {
            final String sha1 = jedis.scriptLoad("" + //
                    "if (redis.call('exists', KEYS[1]) == 0) then " + // 如果KEY不存在
                    " redis.call('hincrby', KEYS[1], ARGV[2], 1); " + // 重入值加1
                    " redis.call('pexpire', KEYS[1], ARGV[1]); " + // 设置 KEY的过期时间(毫秒)
                    " return nil; " + // 返回NULL
                    "end; " + //
                    "if (redis.call('hexists', KEYS[1], ARGV[2]) == 1) then " + // 哈希表的指定字段存在
                    " redis.call('hincrby', KEYS[1], ARGV[2], 1); " + // 哈希表中的字段值加上指定增量值
                    " redis.call('pexpire', KEYS[1], ARGV[1]); " + // 设置 KEY的过期时间(毫秒)
                    " return nil; " + // 返回NULL
                    "end; " + //
                    "return redis.call('pttl', KEYS[1]);"// 以毫秒为单位返回 key 的剩余过期时间
            );
            final List<String> keys = Collections.singletonList(lockKey);
            final List<String> args = Arrays.asList(Long.toString(internalLockLeaseTime), getEntryName(threadId));
            final Long status = (Long) jedis.evalsha(sha1, keys, args);
            return status;
        } catch (Exception e) {
            return 5L;
        }
    }

    /**
     * 尝试释放锁
     * @param threadId 线程ID
     * @return 是否释放成功
     */
    private boolean tryReleaseCommand(long threadId) {
        try (Jedis jedis = pool.getResource()) {
            final String sha1 = jedis.scriptLoad("" + //
                    "if (redis.call('hexists', KEYS[1], ARGV[2]) == 0) then " + // 线程是否持有锁
                    " return nil;" + // 不持有返回NULL
                    "end; " + //
                    "local counter = redis.call('hincrby', KEYS[1], ARGV[2], -1); " + // 重入值减1
                    "if (counter > 0) then " + // 表示线程未释放(嵌套lock情况)
                    " redis.call('pexpire', KEYS[1], ARGV[1]); " + // 续期
                    " return 0; " + //
                    "else " + //
                    " redis.call('del', KEYS[1]); " + // 删除KEY(释放锁)
                    " redis.call('publish', KEYS[2], ARGV[3]); " + // 解锁信息发送到指定的频道
                    " return 1; " + //
                    "end; " + //
                    "return nil;"// 未持有锁(说明没有调用lock，直接调用unlock，这是代码逻辑有问题，应当抛出异常)
            );
            final List<String> keys = Arrays.asList(lockKey, pubSubs.getChannelName(name));
            final List<String> args = Arrays.asList(Long.toString(internalLockLeaseTime), getEntryName(threadId), UNLOCK_MESSAGE);
            Number status = (Number) jedis.evalsha(sha1, keys, args);
            if (status == null) {
                throw new IllegalMonitorStateException("attempt to unlock lock, not locked by current thread. thread-id:" + threadId);
            }
            return status.longValue() == 0;
        }
    }

    /**
     * 尝试到期续订
     * @param threadId 线程ID
     * @return 是否续订成功
     */
    private boolean renewExpirationCommand(long threadId) {
        try (Jedis jedis = pool.getResource()) {
            final String sha1 = jedis.scriptLoad("" + //
                    "if (redis.call('hexists', KEYS[1], ARGV[2]) == 1) then " + // 哈希表中的字段是否存在(是否当前线程占有)
                    " redis.call('pexpire', KEYS[1], ARGV[1]); " + // 设置 KEY的过期时间(毫秒)
                    "return 1; " + // 表示续订成功
                    " end; " + //
                    "return 0;" // 表示续订失败(因为锁已经释放，或者非当前线程未持有)
            );
            final List<String> keys = Collections.singletonList(lockKey);
            final List<String> args = Arrays.asList(Long.toString(internalLockLeaseTime), getEntryName(threadId));
            return ((Number) jedis.evalsha(sha1, keys, args)).intValue() == 1;
        }
    }

    /**
     * 获得锁条目名
     * @param threadId 线程ID
     * @return 锁条目名
     */
    protected String getEntryName(long threadId) {
        return PREFIX_ENTRY + ":" + threadId;
    }
}
