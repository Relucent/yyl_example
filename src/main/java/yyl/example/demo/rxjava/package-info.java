/**
 * 一、什么是 RxJava <br>
 * https://github.com/ReactiveX/RxJava<br>
 * RxJava is a Java VM implementation of Reactive Extensions: <br>
 * a library for composing asynchronous and event-based programs by using observable sequences.<br>
 * (RxJava是一个java虚拟机实现的Reactive扩展： 使用可观测的序列来组成异步的、基于事件的程序的库)<br>
 * 
 * 二、基本概念<br>
 * 简单的来说：RxJava是响应式程序设计的一种实现，是一种扩展的观察者模式，它继承观察者模式，支持序列数据或者事件。<br>
 * 
 * RxJava 有四个基本概念：Observable (可观察者，即被观察者)、 Observer (观察者)、 subscribe (订阅)、事件。<br>
 * Observable 和 Observer 通过 subscribe() 方法实现订阅关系，从而 Observable 可以在需要的时候发出事件来通知 Observer。<br>
 * 
 * 与传统观察者模式不同， RxJava 的事件回调方法除了普通事件 onNext() 之外，还定义了两个特殊的事件：onCompleted() 和 onError()。<br>
 * onCompleted(): 事件队列完结。RxJava 不仅把每个事件单独处理，还会把它们看做一个队列。RxJava 规定，当不会再有新的 onNext() 发出时，需要触发 onCompleted() 方法作为标志。<br>
 * onError(): 事件队列异常。在事件处理过程中出异常时，onError() 会被触发，同时队列自动终止，不允许再有事件发出。<br>
 * 在一个正确运行的事件序列中, onCompleted() 和 onError() 有且只有一个，并且是事件序列中的最后一个。<br>
 * 注意：onCompleted() 和 onError() 二者是互斥的，即在队列中调用了其中一个，就不应该再调用另一个。<br>
 * 
 * 三、RxJava 的使用<br>
 * (1) 创建 Observer <br>
 * (2) 创建 Observable<br>
 * (3) Subscribe (订阅)<br>
 * 线程控制 —— Scheduler (一)
 * 
 * 四、线程控制 <br>
 * 在不指定线程的情况下， RxJava 遵循的是线程不变的原则(生产事件和消费事件在一个线程)，如果需要切换线程，就需要用到 Scheduler（调度器）。 <br>
 * RxJava 通过Scheduler来指定每一段代码应该运行在什么样的线程。<br>
 * RxJava 已经内置了几个 Scheduler ，它们已经适合大多数的使用场景：<br>
 * 
 * Schedulers.immediate(): 直接在当前线程运行，相当于不指定线程。这是默认的 Scheduler。<br>
 * Schedulers.newThread(): 总是启用新线程，并在新线程执行操作。<br>
 * Schedulers.io(): I/O 操作（读写文件、读写数据库、网络信息交互等）所使用的 Scheduler。行为模式和 newThread() 差不多，区别在于 io() 的内部实现是是用一个无数量上限的线程池，可以重用空闲的线程，因此多数情况下 io() 比 newThread()
 * 更有效率。不要把计算工作放在 io() 中，可以避免创建不必要的线程。<br>
 * Schedulers.computation(): 计算所使用的 Scheduler。这个计算指的是 CPU 密集型计算，即不会被 I/O 等操作限制性能的操作，例如图形的计算。这个 Scheduler 使用的固定的线程池，大小为 CPU 核数。 (不要把 I/O 操作放在
 * computation() 中，否则 I/O 操作的等待时间会浪费 CPU。)<br>
 * AndroidSchedulers.mainThread(): Android 专用 ，它指定的操作将在 Android 主线程运行。<br>
 * 
 * 有了这几个 Scheduler ，就可以使用 subscribeOn() 和 observeOn() 两个方法来对线程进行控制。<br>
 * subscribeOn() 指定的就是发射事件的线程。<br>
 * observerOn 指定的就是订阅者接收事件的线程。 <br>
 * 注意：<br>
 * 多次指定发射事件的线程只有第一次指定的有效，也就是说多次调用 subscribeOn() 只有第一次的有效，其余的会被忽略。 <br>
 * 但多次指定订阅者接收线程是可以的，也就是说每调用一次 observerOn()，下游的线程就会切换一次。 <br>
 */
package yyl.example.demo.rxjava;