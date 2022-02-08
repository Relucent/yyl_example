package yyl.example.demo.sqlite;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class SqliteExample {

	public static void main(String[] args) throws Exception {
		File dbFile = new File(System.getProperty("user.dir"), "/sqlite-test" + System.currentTimeMillis() + ".db");

		System.out.println("Create DB File: " + dbFile);
		dbFile.createNewFile();

		Class.forName("org.sqlite.JDBC");
		try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbFile.getAbsolutePath())) {
			System.out.println(conn);
			try (Statement st = conn.createStatement()) {
				st.executeUpdate("drop table if exists test;");
				st.executeUpdate("create table test(id,value);");
				try (PreparedStatement ps = conn.prepareStatement("insert into test (id,value) values (?, ?);")) {
					ps.setInt(1, 1);
					ps.setString(2, "hello world");
					ps.addBatch();
					ps.setInt(1, 2);
					ps.setString(2, "welcome");
					ps.addBatch();
					conn.setAutoCommit(false);
					ps.executeBatch();
					conn.setAutoCommit(true);
					try (ResultSet rs = st.executeQuery("select * from test;")) {
						while (rs.next()) {
							System.out.println("{id=" + rs.getString("id") + ",value=" + rs.getString("value") + "}");
						}
					}
				}
				st.executeUpdate("drop table if exists test;");
			}
		}

		System.out.println("Delete DB File: " + dbFile);
		dbFile.delete();
	}
}