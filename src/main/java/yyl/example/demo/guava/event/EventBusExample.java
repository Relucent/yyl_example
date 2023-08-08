package yyl.example.demo.guava.event;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

/**
 * EventBus 是 Guava 的事件处理机制，是观察者模式（生产/消费模型）的一种实现。<br>
 * 优点：<br>
 * 1、相比 Observer 编程简单方便<br>
 * 2、通过自定义参数可实现同步、异步操作以及异常处理<br>
 * 3、单进程使用，无网络影响<br>
 * 缺点：<br>
 * 1、只能单进程使用<br>
 * 2、项目异常重启或者退出不保证消息持久化<br>
 * 如果需要分布式使用还是需要使用 MQ
 */
public class EventBusExample {

    public static void main(String[] args) {
        EventBus eventBus = new EventBus();
        eventBus.register(new EventListener());
        eventBus.post(1);
        eventBus.post(2);
        eventBus.post(3);
        eventBus.post("1");
        eventBus.post("2");
        eventBus.post("3");
    }

    /**
     * 订阅者<br>
     * 在 Guava EventBus 中，是根据参数类型进行订阅，每个订阅的方法只能由一个参数，同时需要使用 <code>@Subscribe</code>标识<br>
     */
    private static class EventListener {

        /**
         * 监听 Integer 类型的消息
         * @param event 数据
         */
        @Subscribe
        public void listenInteger(Integer event) {
            System.out.println("EventListener#listenInteger ->" + event);
        }

        /**
         * 监听 String 类型的消息
         * @param event 数据
         */
        @Subscribe
        public void listenString(String event) {
            System.out.println("EventListener#listenString ->" + event);
        }
    }
}
