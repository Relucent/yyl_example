package yyl.example.demo.rxjava;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;

/**
 * RxJava 2.x 也为我们保留了简化订阅方法，我们可以根据需求，进行相应的简化订阅，只不过传入对象改为了 Consumer。
 */
public class RxJavaDemo2 {

	public static void main(String[] args) {

		// 创建 观察员 (即消费者) Consumer
		Consumer<Integer> observer = new Consumer<Integer>() {
			@Override
			public void accept(Integer t) throws Exception {
				System.out.println("accept: " + t);
			}
		};
		//| JDK8 可以使用lambda 简写成
		//| observer = t -> System.out.println("accept: " + t);

		//1.初始化被观察对象 Observable
		Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
			@Override
			public void subscribe(ObservableEmitter<Integer> e) throws Exception {
				System.out.println("<");
				for (int i = 0; i < 10; i++) {
					e.onNext(i);
				}
				e.onComplete();
				System.out.println("/>");
			}
		});

		// 3.建立订阅关系 
		observable.subscribe(observer);
	}
}
