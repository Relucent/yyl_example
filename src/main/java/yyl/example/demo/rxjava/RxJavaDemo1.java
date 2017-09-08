package yyl.example.demo.rxjava;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * RxJava是响应式程序设计的一种实现。它继承观察者模式，支持序列数据或者事件。<br>
 */
public class RxJavaDemo1 {

	public static void main(String[] args) {

		// 创建 Observable
		Observer<Integer> observer = new Observer<Integer>() {
			protected Disposable disposable;

			@Override
			public void onSubscribe(Disposable d) {
				this.disposable = d;
				System.out.println("onSubscribe: " + d.getClass());
			}

			@Override
			public void onNext(Integer t) {

				System.out.println("onNext: " + t);

				//切断的操作，让Observer观察者不再接收上游事件
				//disposable.dispose();
				disposable.isDisposed();
			}

			@Override
			public void onError(Throwable e) {
				System.out.println(e);
			}

			@Override
			public void onComplete() {
				System.out.println("::onComplete()");
			}
		};

		// 创建 Observer
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

		// 订阅
		observable.subscribe(observer);
	}
}
