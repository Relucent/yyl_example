/**
 * Java 8 中的 Stream 是对集合（Collection）对象功能的增强，它专注于对集合对象进行各种非常便利、高效的聚合操作（aggregate operation），或者大批量数据操作 (bulk data operation)。<br>
 * Stream API 借助于同样新出现的 Lambda 表达式，极大的提高编程效率和程序可读性。<br>
 * 同时它提供串行和并行两种模式进行汇聚操作，并发模式能够充分利用多核处理器的优势，使用 fork/join 并行方式来拆分任务和加速处理过程。<br>
 * 通常编写并行代码很难而且容易出错, 但使用 Stream API 无需编写一行多线程的代码，就可以很方便地写出高性能的并发程序。<br>
 * Java 8 中首次出现的 java.util.stream 是一个函数式语言+多核时代综合影响的产物。<br>
 */
package yyl.example.basic.jdk8.stream;
