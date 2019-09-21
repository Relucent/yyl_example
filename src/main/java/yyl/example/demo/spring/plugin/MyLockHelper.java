package yyl.example.demo.spring.plugin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.beans.factory.InitializingBean;

/**
 * <h5>分布式锁工具类(基于MySql行锁的分布式锁)</h5> <br>
 * 支持 Spring中使用，配置示例如下:<br>
 * 
 * <pre>
 *	<bean id="myLockHelper" class="yyl.spring.plug.concurrent.lock.MyLockHelper">
 *		<constructor-arg index="0">
 *			<bean class="org.apache.commons.dbcp.BasicDataSource" destroy-method="close">
 *				<property name="driverClassName" value="com.mysql.jdbc.Driver" />
 *				<property name="url" value="${jdbc.url}" />
 *				<property name="username" value="${jdbc.username}" />
 *				<property name="password" value="${jdbc.password}" />
 *				<property name="maxActive" value="10" />
 *				<property name="maxIdle" value="5" />
 *				<property name="defaultAutoCommit" value="false" />
 *				<property name="timeBetweenEvictionRunsMillis" value="3600000" />
 *				<property name="minEvictableIdleTimeMillis" value="3600000" />
 *			</bean>
 *		</constructor-arg>
 *	</bean>
 * </pre>
 * 
 * 使用方式如下：<br>
 * 
 * <pre>
 * 
 * myLockHelper.lock("lock0");// 加锁,参数是锁ID; 与下面的try之间不能有其他代码
 * try {
 *     // 业务代码
 * } finally {
 *     System.out.println(name + "(" + index + ")unlock");
 *     myLockHelper.unlock("lock0");// 必须在finally块中释放锁!
 * }
 * </pre>
 * 
 * @version 20160809
 */
public class MyLockHelper implements InitializingBean {

    // ==============================Constant=========================================
    /** 建表语句 */
    private static final String CREATE_SQL =
            "CREATE TABLE IF NOT EXISTS yyl_concurrent_lock (`id` varchar(255) NOT NULL,`time` datetime NOT NULL,PRIMARY KEY (`id`) ) ENGINE=InnoDB DEFAULT CHARSET=utf8";
    /** 行锁语句 */
    private static final String UPDATE_SQL =
            "INSERT INTO concurrent_lock (id,time) VALUES (?,now()) ON DUPLICATE KEY UPDATE time=now()";

    // ==============================Fields===========================================
    private final ConcurrentMap<String, LockHolder> locks = new ConcurrentHashMap<String, LockHolder>();
    private final javax.sql.DataSource dataSource;

    // ==============================Constructors=====================================
    /**
     * 构造函数
     * @param dataSource 数据源
     */
    public MyLockHelper(javax.sql.DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // ==============================Methods==========================================
    /**
     * 加锁
     * @param id 锁ID
     */
    public void lock(String id) {
        LockHolder holder = getLockHolder(this.locks, id);
        try {
            holder.lock.lock();
        } finally {
            if (holder.currentDepth.get().getAndIncrement() == 0) {
                try {
                    Connection conn = holder.currentConnection.get();
                    if (conn != null) {
                        throw new RuntimeException("Dual thread conflict!");
                    }
                    holder.currentConnection.set(conn = dataSource.getConnection());
                    conn.setAutoCommit(false);
                    conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
                    PreparedStatement ps = conn.prepareStatement(UPDATE_SQL);
                    ps.setString(1, holder.id);
                    ps.execute();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * 解锁
     * @param id 锁ID
     */
    public void unlock(String id) {
        LockHolder holder = getLockHolder(this.locks, id);
        try {
            if (holder.currentDepth.get().decrementAndGet() == 0) {
                Connection conn = holder.currentConnection.get();
                try {
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
                } finally {
                    holder.currentConnection.remove();
                }
            }
        } finally {
            holder.lock.unlock();
        }
    }

    // ==============================ToolsMethods=====================================
    /**
     * 获得一个锁句柄
     * @param locks 锁集合
     * @param id 锁的ID
     * @return 锁句柄
     */
    private static LockHolder getLockHolder(ConcurrentMap<String, LockHolder> locks, String id) {
        while (true) {
            LockHolder holder = locks.get(id);
            if (holder != null) {
                return holder;
            }
            LockHolder sneaky = locks.putIfAbsent(id, new LockHolder(id));
            if (sneaky == null) {
                return locks.get(id);
            } else {
                // Trying again...
            }
        }
    }

    // ==============================SpringMethods====================================
    /** Spring加载BEAN后执行的方法 */
    @Override
    public void afterPropertiesSet() throws Exception {
        Connection conn = null;
        Statement st = null;
        try {
            (st = (conn = dataSource.getConnection()).createStatement()).execute(CREATE_SQL);
        } finally {
            try {
                st.close();
            } catch (Exception x) {
                // ignore
            }
            try {
                conn.close();
            } catch (Exception x) {
                // ignore
            }
        }
    }

    // ==============================InternalClass====================================
    /** 锁句柄 */
    private final static class LockHolder {
        private final String id;
        private final Lock lock;
        private final ThreadLocal<AtomicInteger> currentDepth;
        private final ThreadLocal<Connection> currentConnection;

        LockHolder(String id) {
            this.id = id;
            this.lock = new ReentrantLock();
            this.currentConnection = new ThreadLocal<Connection>();
            this.currentDepth = new ThreadLocal<AtomicInteger>() {
                protected AtomicInteger initialValue() {
                    return new AtomicInteger();
                };
            };
        }
    }
}
