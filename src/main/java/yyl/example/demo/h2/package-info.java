/**
 * H2是一个短小精干的嵌入式数据库引擎，主要的特性包括：<br>
 * 1、免费、开源、快速；<br>
 * 2、嵌入式的数据库服务器，支持集群；<br>
 * 3、提供JDBC、ODBC访问接口，提供基于浏览器的控制台管理程序；<br>
 * 4、Java编写，可使用GCJ和IKVM.NET编译；<br>
 * 5、短小精干的软件，1M左右。<br>
 */
package yyl.example.demo.h2;

//#| 嵌入式（本地）连接<br>
//#| jdbc:h2:[file:][<path>]<databaseName><br>
//#| jdbc:h2:~/test
//#| jdbc:h2:file:/data/sample
//#| jdbc:h2:file:C:/data/sample (Windows only)<br>
//#| 
//#| 内存数据库（私有）
//#| jdbc:h2:mem:<br>
//#| 
//#| 内存数据库（被命名）
//#| jdbc:h2:mem:<databaseName>
//#| jdbc:h2:mem:test_mem
//#| 
//#| 使用TCP/IP的服务器模式（远程连接）
//#| jdbc:h2:tcp://<server>[:<port>]/[<path>]<databaseName>
//#| jdbc:h2:tcp://localhost/~/test
//#| jdbc:h2:tcp://dbserv:8084/~/sample
//#| 
//#| 使用SSL/TLS的服务器模式（远程连接）
//#| jdbc:h2:ssl://<server>[:<port>]/<databaseName>
//#| jdbc:h2:ssl://secureserv:8085/~/sample;
//#| 
//#| 使用加密文件
//#| jdbc:h2:<url>;CIPHER=[AES|XTEA]
//#| jdbc:h2:ssl://secureserv/~/testdb;CIPHER=AES
//#| jdbc:h2:file:~/secure;CIPHER=XTEA
//#| 
//#| 文件锁
//#| jdbc:h2:<url>;FILE_LOCK={NO|FILE|SOCKET}
//#| jdbc:h2:file:~/quickAndDirty;FILE_LOCK=NO
//#| jdbc:h2:file:~/private;CIPHER=XTEA;FILE_LOCK=SOCKET
//#| 
//#| 仅打开存在的数据库
//#| jdbc:h2:<url>;IFEXISTS=TRUE
//#| jdbc:h2:file:~/sample;IFEXISTS=TRUE
//#| 
//#| 当虚拟机退出时并不关闭数据库
//#| jdbc:h2:<url>;DB_CLOSE_ON_EXIT=FALSE
//#| 
//#| 用户名和密码
//#| jdbc:h2:<url>[;USER=<username>][;PASSWORD=<value>]
//#	jdbc:h2:file:~/sample;USER=sa;PASSWORD=123
//#| 
//#| 更新记入索引
//#| 
//#| jdbc:h2:<url>;LOG=2
//#| jdbc:h2:file:~/sample;LOG=2
//#| 
//#| 调试跟踪项设置
//#| jdbc:h2:<url>;TRACE_LEVEL_FILE=<level 0..3>
//#| jdbc:h2:file:~/sample;TRACE_LEVEL_FILE=3
//#| 
//#| 忽略位置参数设置
//#| jdbc:h2:<url>;IGNORE_UNKNOWN_SETTINGS=TRUE
//#| 
//#| 指定文件读写模式
//#| jdbc:h2:<url>;ACCESS_MODE_LOG=rws;ACCESS_MODE_DATA=rws
//#| 
//#| 在Zip文件中的数据库
//#| jdbc:h2:zip:<zipFileName>!/<databaseName>
//#| jdbc:h2:zip:~/db.zip!/test
//#| 
//#| 兼容模式
//#| jdbc:h2:<url>;MODE=<databaseType>
//#| jdbc:h2:~/test;MODE=MYSQL
//#| 
//#| 自动重连接
//#| jdbc:h2:<url>;AUTO_RECONNECT=TRUE
//#| jdbc:h2:tcp://localhost/~/test;AUTO_RECONNECT=TRUE
//#| 
//#| 自动混合模式
//#| jdbc:h2:<url>;AUTO_SERVER=TRUE
//#| jdbc:h2:~/test;AUTO_SERVER=TRUE
//#| 
//#| 更改其他设置
//#| jdbc:h2:<url>;<setting>=<value>[;<setting>=<value>...]
//#| jdbc:h2:file:~/sample;TRACE_LEVEL_SYSTEM_OUT=3
//#|
//#| 支持数据类型
//#| 整数（INT） -2147483648 到 2147483647 -> java.lang.Integer
//#| 布尔型（BOOLEAN）TRUE 和 FALSE ->java.lang.Boolean
//#| 微整数（TINYINT）-128 到 127 ->java.lang.Byte
//#| 小整数（SMALLINT）-32768 到 32767 ->java.lang.Short
//#| 大整数（BIGINT）  -9223372036854775808 到 9223372036854775807 -> java.lang.Long
//#| 标识符（IDENTITY）自增值，值的范围为： -9223372036854775808 到 9223372036854775807。使用的值不能再重用，即使事务回滚。-> java.lang.Long.
//#| 货币数（DECIMAL） 固定整数位和小数位。这个数据类型经常用于存储货币等类型的值 -> java.math.BigDecimal.
//#| 双精度实数（DOUBLE）浮点数。不能应用到表示货币等值，因为有四舍五入的问题。-> java.lang.Double
//#| 实数（REAL）单精度浮点数。（不能应用到表示货币等值，因为有四舍五入的问题）->java.lang.Float
//#| 时间（TIME）hh:mm:ss -> java.sql.Time
//#| 日期（DATE）yyyy-MM-dd -> java.sql.Date
//#| 时间戳（TIMESTAMP） yyyy-MM-dd hh:mm:ss[.nnnnnnnnn] ->java.sql.Timestamp (java.util.Date 也支持).
//#| 二进制（BINARY）表示一个字节数组，最大的尺寸为 2GB ->byte[]
//#| 其他类型（OTHER）这个类型允许存储可序列化的JAVA对象(在内部，使用的是一个字节数组)序列化和反序列化只在客户端端完成 -> java.lang.Object 
//#| 可变字符串（VARCHAR）->java.lang.String
//#| 不区分大小写可变字符串（VARCHAR_IGNORECASE）->java.lang.String
//#| 字符（CHAR）->java.lang.String
//#| 二进制大对象（BLOB）->java.sql.Blob (java.io.InputStream 也支持)。
//#| 文本大对象（CLOB） ->java.sql.Clob (java.io.Reader也支持)
//#| 通用唯一标识符（UUID） ->java.util.UUID.
//#| 数组（ARRAY）-> java.lang.Object[] 