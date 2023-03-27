package yyl.example.demo.shedlock;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

import javax.sql.DataSource;

import net.javacrumbs.shedlock.core.LockConfiguration;
import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.core.SimpleLock;
import net.javacrumbs.shedlock.provider.jdbc.JdbcLockProvider;
import yyl.example.basic.jdbc.lock.DataSourceUtil;

/**
 * ShedLock 的独立使用方式<br>
 * 
 * <pre>
 * 初始化SQL
 * CREATE TABLE shedlock(name VARCHAR(64) COMMENT '每个定时任务名字',lock_until TIMESTAMP(3) COMMENT '锁的结束时间',locked_at TIMESTAMP(3) COMMENT '锁的开始时间',locked_by VARCHAR(255) COMMENT '服务名称',PRIMARY KEY (NAME)) COMMENT='定时任务锁';
 * </pre>
 */
public class ShedlockExample {

    public static void main(String[] args) {

        // 获得一个数据源
        DataSource dataSource = DataSourceUtil.getDataSource();
        System.out.println(dataSource);
        // 创建 LockProvider 对象
        LockProvider lockProvider = new JdbcLockProvider(dataSource);

        Instant now = Instant.now();
        String lockName = "myLockName";// 锁的名称
        Duration lockAtMostFor = Duration.ofSeconds(60);// 最大持锁时间，可选参数。表示获取锁后的最大持锁时间，超过该时间则自动释放锁。
        Duration lockAtLeastFor = Duration.ofSeconds(10);// 最小持锁时间。表示获取锁后的最小持锁时间，锁至少持有该时间，即使执行任务已经完成了。

        LockConfiguration lockConfiguration = new LockConfiguration(now, lockName, lockAtMostFor, lockAtLeastFor);

        // 获取锁(如果无法获取锁，则返回NULL）
        Optional<SimpleLock> lockOptional = lockProvider.lock(lockConfiguration);

        if (lockOptional.isPresent()) {
            try {
                // 执行任务
                System.out.println("Task is running...");
            } finally {
                // 释放锁
                lockOptional.get().unlock();
            }
        } else {
            System.out.println("Task is already running, skipping...");
        }
    }
}
