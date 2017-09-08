package yyl.example.demo.rxjava;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * 使用interval(),创建一个按固定时间间隔发射整数序列的Observable，可用作定时器 <br>
 */
public class RxJavaDemo6 {

	public static void main(String[] args) throws Exception {
		Observable.interval(1, TimeUnit.MILLISECONDS).subscribe(new Consumer<Long>() {
			@Override
			public void accept(@NonNull Long t) throws Exception {
				System.out.println(t);
			}
		});
		Thread.sleep(1000);
	}
}
