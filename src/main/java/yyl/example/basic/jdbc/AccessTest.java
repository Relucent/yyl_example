package yyl.example.basic.jdbc;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import yyl.example.basic.util.ClassPath;

public class AccessTest {
	public static void main(String args[]) throws Exception {
		String mdbsrc = new File(ClassPath.getPathFromClass(AccessTest.class)).getParent() + "/test.mdb";
		String strurl = "jdbc:odbc:driver={Microsoft Access Driver (*.mdb)};DBQ=" + mdbsrc;
		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		Connection conn = DriverManager.getConnection(strurl);
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select * from TEST");
		while (rs.next()) {
			System.out.println(rs.getString("id") + ":" + rs.getString("name"));
		}
		rs.close();
		stmt.close();
		conn.close();
	}
}