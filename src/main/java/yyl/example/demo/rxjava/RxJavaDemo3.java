package yyl.example.demo.rxjava;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.schedulers.Schedulers;

/**
 * 背压(BackPressure)是指在异步场景中，被观察者发送事件速度远快于观察者的处理速度的情况下，一种告诉上游的被观察者降低发送速度的策略。
 */
public class RxJavaDemo3 {

	public static void main(String[] args) {
		// 1  初始化订阅者(观察者)
		Subscriber<String> subscriber = new Subscriber<String>() {
			Subscription subscription;

			@Override
			public void onSubscribe(Subscription s) {
				subscription = s;
				//我们通常可以在这里做一些初始化操作，调用request()方法表示初始化工作已经完成
				System.out.println("onSubscribe");

				//调用request()方法，会立即触发onNext()方法
				s.request(Long.MAX_VALUE);//
			}

			@Override
			public void onNext(String value) {
				System.out.println(value);
				waitFor(1000);
				subscription.request(2);
			}

			@Override
			public void onError(Throwable t) {
				System.out.println("onError");
			}

			@Override
			public void onComplete() {
				System.out.println("onComplete");
			}
		};

		//2  消息发布者
		Flowable<String> flowable = Flowable.create(new FlowableOnSubscribe<String>() {
			@Override
			public void subscribe(FlowableEmitter<String> e) throws Exception {
				System.out.println("Emit->");
				e.onNext("Hello RxJava!");
				e.onNext("Hello RxJava!");
				e.onNext("Hello RxJava!");
				e.onNext("Hello RxJava!");
				e.onNext("Hello RxJava!");
				e.onComplete();
			}
		}, BackpressureStrategy.BUFFER);

		// 3 订阅
		flowable.subscribeOn(Schedulers.newThread())//指定发射事件的线程发生在 IO 线程
				.observeOn(Schedulers.newThread())//指定的就是订阅者总是启用新线程
				.subscribe(subscriber);

		waitFor(10000);
	}

	private static void waitFor(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
