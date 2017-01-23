package yyl.example.basic.jdbc.lock;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

/**
 * 分布式锁工厂(基于MySql行锁的分布式锁工厂)
 */
public class MyLockFactory {

	private final ConcurrentHashMap<String, MyLock> locks = new ConcurrentHashMap<String, MyLock>();
	private final DataSource dataSource;

	/**
	 * 构造函数
	 * @param dataSource 数据源
	 */
	public MyLockFactory(DataSource dataSource) {
		this.dataSource = dataSource;
		initialize();
	}

	/**
	 * 获得一个数据库分布式锁
	 * @param id 锁的ID
	 * @return 分布式锁
	 */
	public MyLock getLock(String id) {
		while (true) {
			MyLock lock = locks.get(id);
			if (lock != null) {
				return lock;
			}
			MyLock sneaky = locks.putIfAbsent(id, new MyLock(id, dataSource));
			if (sneaky == null) {
				return locks.get(id);
			} else {
				//Trying again...
			}
		}
	}

	public void initialize() {
		Connection conn = null;
		Statement st = null;
		try {
			(st = (conn = dataSource.getConnection()).createStatement()).execute(
					"CREATE TABLE IF NOT EXISTS concurrent_lock (`id` varchar(255) NOT NULL,`time` datetime NOT NULL,PRIMARY KEY (`id`) ) ENGINE=InnoDB DEFAULT CHARSET=utf8");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				st.close();
			} catch (Exception x) {
				//ignore
			}
			try {
				conn.close();
			} catch (Exception x) {
				//ignore
			}
		}
	}

}
