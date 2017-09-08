package yyl.example.demo.rxjava;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 在不指定线程的情况下， RxJava 遵循的是线程不变的原则(生产事件和消费事件在一个线程)，如果需要切换线程，就需要用到 Scheduler（调度器）。 <br>
 */
public class RxJavaDemo5 {

	public static void main(String[] args) throws Exception {

		Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9)//
				.subscribeOn(Schedulers.newThread())//指定发射事件的线程发生在 IO 线程
				.observeOn(Schedulers.newThread())//指定的就是订阅者总是启用新线程
				.doOnNext(new Consumer<Integer>() {
					@Override
					public void accept(@NonNull Integer integer) throws Exception {
						System.out.println("doOnNext:" + Thread.currentThread());
					}
				}).observeOn(Schedulers.newThread())//指定的就是订阅者总是启用新线程
				.subscribe(new Consumer<Integer>() {
					@Override
					public void accept(@NonNull Integer integer) throws Exception {
						System.out.println("subscribe:" + Thread.currentThread());
					}
				});

		//等待执行完成
		Thread.sleep(1000);
	}
}
