package yyl.example.basic.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBTest {

	public static void main(String[] args) {
		printlnCurrentDate("com.ibm.db2.jcc.DB2Driver", "jdbc:db2://localhost:50000/datebase", "admin", "admin", "values current timestamp");
		printlnCurrentDate("oracle.jdbc.driver.OracleDriver", "jdbc:oracle:thin:@localhost:1521:orcl", "sa", "sa", "select systimestamp from dual");
		printlnCurrentDate("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/test?useUnicode=true&amp;characterEncoding=utf-8", "root", "root", "select now()");
	}

	public static void printlnCurrentDate(String driverName, String url, String name, String password, String sql) {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			Class.forName(driverName);
			conn = DriverManager.getConnection(url, name, password);
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			if (rs.next()) {
				System.out.println(rs.getTimestamp(1));
			} else {
				System.out.println("null");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.closeQuietly(rs);
			DbUtil.closeQuietly(st);
			DbUtil.closeQuietly(conn);
		}
	}
}
