package yyl.example.basic.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

/**
 * JDBC支持类,用于获得数据库连接<br>
 * 为方便使用采用静态调用<br>
 */
public class DbUtil {

	// ==============================Fields===========================================
	private static final String MYSQL_CLASS_LOAD = "com.mysql.jdbc.Driver";
	private static final String ORACLE_CLASS_LOAD = "oracle.jdbc.driver.OracleDriver";
	private static final String DB2_CLASS_LOAD = "com.ibm.db2.jcc.DB2Driver";
	private static final String MSSQL_2000_CLASS_LOAD = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
	private static final String MSSQL_2008_CLASS_LOAD = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private static final String POSTGRESQL_CLASS_LOAD = "org.postgresql.Driver";

	private static final String MYSQL_URL_PREFIX = "jdbc:mysql:";
	private static final String ORACLE_URL_PREFIX = "jdbc:oracle:";
	private static final String DB2_URL_PREFIX = "jdbc:db2:";
	private static final String MSSQL_2000_URL_PREFIX = "jdbc:microsoft:sqlserver:";
	private static final String MSSQL_2008_URL_PREFIX = "jdbc:sqlserver";
	private static final String POSTGRESQL_URL_PREFIX = "jdbc:postgresql:";

	// ==============================Methods==========================================
	/**
	 * 获得数据库连接
	 * @param url 数据库连接地址
	 * @param username 用户名
	 * @param password 密码
	 * @return 数据库连接
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws Exception
	 */
	public static Connection getConnection(String url, String username, String password) throws SQLException, ClassNotFoundException {
		String driver;
		if (url.startsWith(MYSQL_URL_PREFIX)) {
			driver = MYSQL_CLASS_LOAD;
		} else if (url.startsWith(ORACLE_URL_PREFIX)) {
			driver = ORACLE_CLASS_LOAD;
		} else if (url.startsWith(DB2_URL_PREFIX)) {
			driver = DB2_CLASS_LOAD;
		} else if (url.startsWith(MSSQL_2000_URL_PREFIX)) {
			driver = MSSQL_2000_CLASS_LOAD;
		} else if (url.startsWith(MSSQL_2008_URL_PREFIX)) {
			driver = MSSQL_2008_CLASS_LOAD;
		} else if (url.startsWith(POSTGRESQL_URL_PREFIX)) {
			driver = POSTGRESQL_CLASS_LOAD;
		} else {
			throw new SQLException("unrecognized jdbc url!");
		}
		return getConnection(driver, url, username, password);
	}

	/**
	 * 获得数据库连接
	 * @param driver 驱动类名称
	 * @param url 数据库连接地址
	 * @param username 用户名
	 * @param password 密码
	 * @return 数据库连接
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static Connection getConnection(String driver, String url, String username, String password) throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		return DriverManager.getConnection(url, username, password);
	}

	/**
	 * 获得数据库连接
	 * @param dataSource 数据源
	 * @return 数据库连接
	 * @throws SQLException
	 */
	public static Connection getConnection(DataSource dataSource) throws SQLException {
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			throw e;
		}
	}

	/**
	 * 获得数据库连接
	 * @param autoCommit 事物是否自动提交
	 * @return 数据库连接
	 * @throws SQLException
	 */
	public static Connection getConnection(DataSource dataSource, boolean autoCommit) throws SQLException {
		Connection conn = null;
		try {
			conn = getConnection(dataSource);
			DbUtil.setAutoCommit(conn, autoCommit);
			return conn;
		} catch (SQLException e) {
			throw e;
		}
	}

	/**
	 * 关闭结果集
	 * @param rs 结果集
	 */
	public static void closeQuietly(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 关闭查询对象
	 * @param st 查询对象
	 */
	public static void closeQuietly(Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 关闭数据库连接
	 * @param conn 数据库连接
	 */
	public static void closeQuietly(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 设置事务是否自动提交
	 * @param connection 数据库连接
	 * @param autoCommit 事物是否自动提交
	 */
	public static void setAutoCommit(Connection conn, boolean autoCommit) {
		if (conn != null) {
			try {
				conn.setAutoCommit(autoCommit);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 设置事务级别
	 * @param connection 数据库连接
	 */
	public static void setTransactionIsolation(Connection connection, int transactionIsolation) {
		if (connection != null) {
			try {
				connection.setTransactionIsolation(transactionIsolation);
			} catch (SQLException e) {
			}
		}
	}

	/**
	 * 提交事务
	 * @param connection 数据库连接
	 * @throws SQLException
	 */
	public static void commit(Connection connection) throws SQLException {
		connection.commit();
	}

	/**
	 * 回滚事务
	 * @param conn 数据库连接
	 */
	public static void rollback(Connection conn) {
		if (conn != null) {
			try {
				conn.rollback();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 执行单条SQL语句.
	 * @param conn 数据库连接
	 * @param sql SQL语句
	 * @return 删除的记录数
	 */
	public boolean execute(Connection conn, String sql) {
		Statement ps = null;
		try {
			DbUtil.setAutoCommit(conn, false);
			ps = conn.createStatement();
			boolean result = ps.execute(sql);
			DbUtil.commit(conn);
			return result;
		} catch (Exception e) {
			DbUtil.rollback(conn);
			e.printStackTrace();
			return false;
		} finally {
			closeQuietly(ps);
		}
	}

	/**
	 * 将java.util.Date转化为java.sql.Date
	 * @param date java.util.Date
	 * @return java.sql.Date
	 */
	public final static java.sql.Date toSqlDate(java.util.Date date) {
		return new java.sql.Date(date.getTime());
	}

	/**
	 * 将java.util.Date转化为java.sql.Timestamp
	 * @param date java.util.Date
	 * @return java.sql.Timestamp
	 */
	public final static java.sql.Timestamp toTimestamp(java.util.Date date) {
		return new java.sql.Timestamp(date.getTime());
	}

	/**
	 * 获得一组"?"
	 * @param number ?个数
	 * @return ?字符串
	 */
	public final static String toSqlInExpressionQuestion(int number) {
		StringBuilder sbr = new StringBuilder();
		for (int i = 0; i < number; i++) {
			sbr.append(",? ");
		}
		return sbr.substring(1);
	}

	/**
	 * 将参数添加给表达式对象
	 * @param ps 表达式对象
	 * @param parameters 参数表
	 * @param offset 参数索引偏移
	 * @throws SQLException
	 */
	public static void applyParameterToQuery(PreparedStatement ps, List<?> parameters, int offset) throws SQLException {
		if (ps != null && parameters != null) {
			int index = 1 + offset;
			for (Object parameter : parameters) {
				if (parameter instanceof String) {
					ps.setString(index, (String) parameter);
				} else if (parameter instanceof Long) {
					ps.setLong(index, (Long) parameter);
				} else if (parameter instanceof Integer) {
					ps.setInt(index, (Integer) parameter);
				} else if (parameter instanceof Double) {
					ps.setDouble(index, (Double) parameter);
				} else if (parameter instanceof Date) {
					ps.setTimestamp(index, new Timestamp(((Date) parameter).getTime()));
				} else if (parameter instanceof Boolean) {
					ps.setBoolean(index, (Boolean) parameter);
				} else if (parameter == null) {
					ps.setObject(index, null);
				}
				index++;
			}
		}
	}

	// ==============================Constructors=====================================
	protected DbUtil() {
	}
}