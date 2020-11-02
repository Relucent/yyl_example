package yyl.example.demo.jsqlparser;

public class CountSqlParserTest {
    public static void main(String[] args) {
        String sql = "select id,name,value from user where name like '%hello%' order by id desc";
        CountSqlParser countSqlParser = new CountSqlParser();
        System.out.println(countSqlParser.getSmartCountSql(sql));
    }
}
