/**
 * Thrift是一种开源的跨语言的RPC服务框架。<br>
 * Thrift最初由facebook公司开发的，在2007年facebook将其提交apache基金会开源了。<br>
 * 对于当时的facebook来说创造thrift是为了解决facebook系统中各系统间大数据量的传输通信以及系统之间语言环境不同需要跨平台的特性。<br>
 * 所以thrift可以支持多种程序语言，支持的语言如下： <br>
 * C++, Java, Go,Python, PHP, Ruby, Erlang, Perl, Haskell, C#, Cocoa, JavaScript, Node.js, Smalltalk, OCaml<br>
 * @see <a href="http://thrift.apache.org/">thrift</a>
 */
package yyl.example.demo.thrift;

//| Thrift 脚本可定义的数据类型包括以下几种类型：
//| 基本类型：
//|  bool：布尔值，true 或 false，对应 Java 的 boolean
//|  byte：8 位有符号整数，对应 Java 的 byte
//|  i16：16 位有符号整数，对应 Java 的 short
//|  i32：32 位有符号整数，对应 Java 的 int
//|  i64：64 位有符号整数，对应 Java 的 long
//|  double：64 位浮点数，对应 Java 的 double
//|  string：未知编码文本或二进制字符串，对应 Java 的 String
//| 结构体类型：
//|  struct：定义公共的对象，类似于 C 语言中的结构体定义，在 Java 中是一个 JavaBean
//| 容器类型：
//|  list：对应 Java 的 ArrayList
//|  set：对应 Java 的 HashSet
//|  map：对应 Java 的 HashMap
//| 异常类型：
//|  exception：对应 Java 的 Exception
//| 服务类型：
//|  service：对应服务的类