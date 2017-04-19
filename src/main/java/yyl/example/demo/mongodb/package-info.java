/**
 * MongoDB 是一个基于分布式文件存储的数据库。由 C++ 语言编写。旨在为 WEB 应用提供可扩展的高性能数据存储解决方案。<br>
 * MongoDB 是一个介于关系数据库和非关系数据库之间的产品。 他支持的数据结构非常松散，是类似json的bson格式，因此可以存储比较复杂的数据类型。<br>
 * MongoDB 将数据存储为一个文档，数据结构由键值(key=>value)对组成。字段值可以包含其他文档，数组及文档数组。<br>
 * MongoDB 最大的特点是他支持的查询语言非常强大，其语法有点类似于面向对象的查询语言，几乎可以实现类似关系数据库单表查询的绝大部分功能，而且还支持对数据建立索引。<br>
 * 
 * 官网地址： [https://www.mongodb.com]<br>
 */
package yyl.example.demo.mongodb;

//#  SQL术语/概念       | MongoDB术语/概念  | 解释/说明
//#  ------------|---------------|------------
//#  database    | database      | 数据库 
//#  table       | collection    | 数据库表/集合 
//#  row         | document      | 数据记录行/文档 
//#  column      | field         | 数据字段/域 
//#  index       | index         | 索引 
//#  table joins |               | 表连接,MongoDB不支持 
//#  primary key | primary key   | 主键,MongoDB自动将_id字段设置为主键 
//#  
//#  MongoDB的数据默认存储在data目录的db目录下，但是这个目录在安装过程不会自动创建，所以需要手动创建data目录，并在data目录中创建db目录。
//#  创建数据库：
//#  mkdir -p /data/db
//#  启动服务：
//#  ./mongod
//#  如果的数据库目录不是/data/db，可以通过 --dbpath 来指定
//#  mongod --dbpath /data/db