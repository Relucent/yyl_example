package yyl.example.basic.jdbc.lock;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

public class DataSourceUtil {

    private static final BasicDataSource dataSource;
    static {
        dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/_demo"//
                + "?serverTimezone=Asia/Shanghai"//
                + "&allowMultiQueries=true"//
                + "&useUnicode=true"//
                + "&characterEncoding=UTF-8"//
                + "&zeroDateTimeBehavior=convertToNull"//
                + "&useSSL=false");
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
