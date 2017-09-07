package yyl.example.demo.rxjava;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;

/**
 * 背压是指在异步场景中，被观察者发送事件速度远快于观察者的处理速度的情况下，一种告诉上游的被观察者降低发送速度的策略。
 */
public class RxJavaDemo3 {

	public static void main(String[] args) {

		// 1  初始化订阅者(观察者)
		Subscriber<String> subscriber = new Subscriber<String>() {
			@Override
			public void onSubscribe(Subscription s) {
				//我们通常可以在这里做一些初始化操作，调用request()方法表示初始化工作已经完成
				System.out.println("onSubscribe");

				//等待5秒
				for (int i = 1; i <= 5; i++) {
					System.out.println(i);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
					}
				}

				//调用request()方法，会立即触发onNext()方法
				s.request(Long.MAX_VALUE);
			}

			@Override
			public void onNext(String value) {
				System.out.println(value);
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
				e.onComplete();
			}
		}, BackpressureStrategy.BUFFER);

		// 3 订阅
		flowable.subscribe(subscriber);
	}
}
