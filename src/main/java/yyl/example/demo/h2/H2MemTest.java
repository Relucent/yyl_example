package yyl.example.demo.h2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 内存模式 H2 例子
 */
public class H2MemTest {

	public static void main(String[] a) throws Exception {
		Class.forName("org.h2.Driver");
		try (Connection conn = DriverManager.getConnection("jdbc:h2:mem:test ", "sa", "")) {
			initializeTable(conn);
			initializeData(conn);
			query(conn);
		}
	}

	private static void initializeTable(Connection conn) throws SQLException {
		try (Statement st = conn.createStatement()) {
			st.execute("create table test(id int primary key not null,value varchar(20) null)");
		}
	}

	private static void initializeData(Connection conn) throws SQLException {
		try (Statement st = conn.createStatement()) {
			st.executeUpdate("insert into test (id, value) values (1,'value-1')");
			st.executeUpdate("insert into test (id, value) values (2,'value-2')");
			st.executeUpdate("insert into test (id, value) values (3,'value-3')");
			st.executeUpdate("insert into test (id, value) values (4,'value-4')");
			st.executeUpdate("insert into test (id, value) values (5,'value-5')");
		}
	}

	private static void query(Connection conn) throws SQLException {
		try (Statement st = conn.createStatement()) {
			try (ResultSet rs = st.executeQuery("SELECT * FROM TEST ")) {
				ResultSetMetaData md = rs.getMetaData();
				int columnCount = md.getColumnCount();
				String[] labels = new String[columnCount];
				for (int i = 0; i < columnCount; i++) {
					System.out.print(labels[i] = md.getColumnLabel(i + 1));
					System.out.print(" | ");
				}
				System.out.println("\n----------");

				while (rs.next()) {
					for (String label : labels) {
						System.out.print(rs.getString(label));
						System.out.print(" | ");
					}
					System.out.println();
				}
			}
		}
	}

}
