package yyl.example.basic.script;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/**
 * 用JavaScript调用JDBC
 */
public class JsExecuteSqlTest {

    public static void main(String args[]) {
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine engine = scriptEngineManager.getEngineByName("javascript");
        try {
            String script = "" //
                    + "var conn,st,rs; \n"//
                    + "try{ \n"//
                    + "  conn = Helper.connection('org.h2.Driver','jdbc:h2:mem:test', 'sa', ''); \n"//
                    + "  st = conn.createStatement(); \n" //
                    + "  st.execute('create table test(id int primary key not null,value varchar(20) null)');\n "//
                    + "  st.executeUpdate('insert into test (id, value) values (1,\\'value-1\\')'); \n "//
                    + "  st.executeUpdate('insert into test (id, value) values (2,\\'value-2\\')'); \n "//
                    + "  st.executeUpdate('insert into test (id, value) values (3,\\'value-3\\')'); \n "//
                    + "  rs = st.executeQuery('select id,value from test'); \n"//
                    + "  while (rs.next()) { \n"//
                    + "    var id = rs.getString('id'); \n"//
                    + "    var value = rs.getString('value'); \n"//
                    + "    print(id + ':' + value); \n"//
                    + "  } \n"//
                    + "}finally{ \n"//
                    + "  Helper.close(rs); \n"//
                    + "  Helper.close(st); \n"//
                    + "  Helper.close(conn); \n"//
                    + "} \n"//
                    + "";
            engine.put("Helper", new Helper());
            engine.eval(script);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class Helper {

        public Connection connection(String driver, String url, String username, String password)
                throws ClassNotFoundException, SQLException {
            Class.forName(driver);
            return DriverManager.getConnection(url, username, password);
        }

        public void close(AutoCloseable c) {
            try {
                c.close();
            } catch (Exception e) {}
        }
    }
}
