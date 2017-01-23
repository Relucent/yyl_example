package yyl.example.basic.jdbc.lock;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;

public class DataSourceUtil {

	private static final BasicDataSource dataSource;
	static {
		dataSource = new BasicDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/demo");
		dataSource.setUsername("root");
		dataSource.setPassword("root");
	}

	public static DataSource getDataSource() {
		return dataSource;
	}

	public static void destroy() {
		try {
			dataSource.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
