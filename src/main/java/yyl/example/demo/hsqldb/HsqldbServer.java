package yyl.example.demo.hsqldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.hsqldb.Server;

/**
 * HSQLDB服务
 */
public class HsqldbServer {
	// ==============================Fields===========================================
	/** 等待数据库停止的最大时间 */
	public static final int WAIT_TIME = 1000;
	/** 登陆用户名 */
	private String username;
	/** 登陆密码 */
	private String password;
	/** 端口 */
	private int port;

	private final String path;
	private final String databaseName;
	private final String url;

	// ==============================Constructors=====================================
	public HsqldbServer(String path, String databaseName, int port) {
		this.path = path;
		this.databaseName = databaseName;
		this.port = port;
		this.url = "jdbc:hsqldb:hsql://localhost:" + port + "/" + databaseName;
	}

	// ==============================Methods==========================================
	public void start() {
		try {
			Server server = new Server();
			server.setDatabasePath(0, path + "/" + databaseName);
			server.setDatabaseName(0, databaseName);
			server.setPort(port);
			server.setSilent(true);
			server.start();
			Thread.sleep(WAIT_TIME);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public Connection getConnection() {
		try {
			Class.forName("org.hsqldb.jdbcDriver");
			return DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void destroy() {
		Connection conn = null;
		Statement ps = null;
		try {
			(ps = (conn = getConnection()).createStatement()).executeUpdate("SHUTDOWN;");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeQuietly(ps);
			closeQuietly(conn);
		}
	}

	private static void closeQuietly(AutoCloseable closeable) {
		try {
			closeable.close();
		} catch (Exception e) {
		}
	}

	// ==============================TestMethods======================================
	public static void main(String[] args) {
		String dir = System.getProperty("user.dir");
		System.out.println("user.dir->" + dir);
		HsqldbServer server = new HsqldbServer(dir + "/db", "test", 9901);
		try {
			server.start();
			Connection conn = server.getConnection();
			System.out.println(conn);
		} finally {
			server.destroy();
		}
	}
}
