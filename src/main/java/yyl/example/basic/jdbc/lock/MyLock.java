package yyl.example.basic.jdbc.lock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.sql.DataSource;

/**
 * 分布式锁(基于MySql行锁的实现)<br>
 */
public class MyLock {

    private final String id;
    private final DataSource dataSource;
    private final ThreadLocal<Connection> currentConnection = new ThreadLocal<Connection>();
    private final AtomicInteger reference = new AtomicInteger();
    private final Lock lock = new ReentrantLock();

    /**
     * 构造函数
     * @param id 锁ID
     * @param dataSource 数据源
     */
    MyLock(String id, DataSource dataSource) {
        this.id = id;
        this.dataSource = dataSource;
    }

    /**
     * 获取锁
     */
    public void lock() {
        lock.lock();
        if (reference.getAndIncrement() == 0) {
            try {
                Connection conn = dataSource.getConnection();
                currentConnection.set(conn);
                conn.setAutoCommit(false);
                conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
                PreparedStatement ps = conn.prepareStatement(
                        "INSERT INTO concurrent_lock (id,time) VALUES (?,now()) ON DUPLICATE KEY UPDATE time=now()");
                ps.setString(1, id);
                ps.execute();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 释放锁
     */
    public void unlock() {
        try {
            if (reference.decrementAndGet() == 0) {
                Connection conn = currentConnection.get();
                if (conn != null) {
                    try {
                        conn.commit();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
                currentConnection.remove();
            }
        } finally {
            lock.unlock();
        }

    }
}
