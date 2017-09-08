package yyl.example.demo.rxjava;

import io.reactivex.Observable;

/**
 * RxJava 还提供了一些方法用来快捷创建事件队列<br>
 */
public class RxJavaDemo4 {

	public static void main(String[] args) {

		// fromArray与just相似，都可以用来发射单个或一组数据
		// 区别在参数是数组的情况(fromArray会逐个发送数据，而just会将数组作为一个参数)
		// 原因在于 fromArray方法接收的是一个可变参数，而just是固定个数的参数

		Integer[] items = { 0, 1, 2, 3, 4, 5 };

		// just(T...): 将传入的参数依次发送出来。
		Observable.just(items).subscribe(t -> {
			System.out.println("just->" + t);
		});

		// fromArray(T[]) : 将传入的数组拆分成具体对象后，依次发送出来。
		Observable.fromArray(items).subscribe(t -> {
			System.out.println("fromArray->" + t);
		});

	}
}
