/**
 * Thrift是一种开源的跨语言的RPC服务框架。<br>
 * Thrift最初由facebook公司开发的，在2007年facebook将其提交apache基金会开源了。<br>
 * 对于当时的facebook来说创造thrift是为了解决facebook系统中各系统间大数据量的传输通信以及系统之间语言环境不同需要跨平台的特性。<br>
 * 所以thrift可以支持多种程序语言，支持的语言如下： <br>
 * C++, Java, Go,Python, PHP, Ruby, Erlang, Perl, Haskell, C#, Cocoa, JavaScript, Node.js, Smalltalk, OCaml<br>
 * @see <a href="http://thrift.apache.org/">thrift</a>
 */
package yyl.example.demo.thrift;

//| 一、 Thrift 脚本可定义的数据类型包括以下几种类型：
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
//|
//| 二、编码基本步骤：
//|  1) 服务端编码基本步骤
//|   实现服务处理接口impl
//|   创建TProcessor
//|   创建TServerTransport
//|   创建TProtocol
//|   创建TServer
//|   启动Server
//|  
//|  2) 客户端编码基本步骤：
//|   创建Transport
//|   创建TProtocol
//|   基于TTransport和TProtocol创建 Client
//|   调用Client的相应方法
//|
//| 三、数据传输协议
//|  TBinaryProtocol : 二进制格式.
//|  TCompactProtocol : 压缩格式
//|  TJSONProtocol : JSON格式
//|  TSimpleJSONProtocol : 提供JSON write-only 协议, 生成的文件很容易通过脚本语言解析
//|
//| 四、JAVA中使用thrift步骤：
//| 1) 定义 thrift 文件，例如 hello.thrift，文件内容
//| 
//|  namespace java yyl.example.demo.thrift
//|  service HelloService {
//|   string hello(1:string text)
//|  }
//| 
//| 2) 使用命令编译 thrift 
//|  (window环境可以下载 thrift.exe)
//| 命令格式： thrift --gen <language> <Thrift filename>
//| 
//| 例如执行命令 thrift-0.10.0.exe -r -gen java ./hello.thrift 
//| 会自动生成一个gen-java的目录，其中有构建的客户端有用的源代码 (HelloService.java)
//|
//| 3) 创建 service handler类
//| 该类需要实现 HelloService.Iface接口
//|
//| 4) 创建服务器代码
//|
//| 5) 创建客户端代码
//| 
//| ...
