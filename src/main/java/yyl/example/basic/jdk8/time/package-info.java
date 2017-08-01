/**
 * 在Java SE 8中，添加了一个新包：java.time，它提供了结构良好的API来处理时间和日期。<br>
 * 新的API：java.time，由5个包组成：<br>
 * java.time – 包含值对象的基础包<br>
 * java.time.chrono – 提供对不同的日历系统的访问<br>
 * java.time.format – 格式化和解析时间和日期<br>
 * java.time.temporal – 包括底层框架和扩展特性<br>
 * java.time.zone – 包含时区支持的类<br>
 */
package yyl.example.basic.jdk8.time;

//# Instant：时间戳 
//# Duration：持续时间，时间差 
//# LocalDate：只包含日期，比如：1995-05-23
//# LocalTime：只包含时间，比如：09:00:00
//# LocalDateTime：包含日期和时间，比如：1995-05-23 09:00:00
//# Period：时间段 
//# ZoneOffset：时区偏移量，比如：+8:00 
//# ZonedDateTime：带时区的时间 
//# Clock：时钟，比如获取目前美国纽约的时间
