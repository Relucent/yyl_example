/**
 * Disruptor是一个高性能的异步处理框架，或者可以认为是消息框架(轻量的JMS)<br>
 * 从功能上来看，Disruptor 是实现了“队列”的功能，而且是一个有界队列。<br>
 * 那么它的应用场景自然就是“生产者-消费者”模型的应用场合。<br>
 * 最大特点是高性能，其LMAX架构可以获得每秒6百万订单，用1微秒的延迟获得吞吐量为100K+。<br>
 */
package yyl.example.demo.disruptor;