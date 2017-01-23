package yyl.example.basic.reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.reflect.Field;

/**
 * 虚引用并不会决定对象的生命周期。如果一个对象仅持有虚引用，那么它就和没有任何引用一样(PhantomReference.get方法永远返回NULL)，在任何时候都可能被垃圾回收器回收。<br>
 * 可以用 ReferenceQueue 来判断垃圾回收的时间点<br>
 */
public class PhantomReferenceTest {
	public static void main(String[] args) throws InterruptedException {
		final ReferenceQueue<String> queue = new ReferenceQueue<String>();

		new Thread() {
			public void run() {
				try {
					@SuppressWarnings("unchecked")
					Reference<String> reference = (Reference<String>) queue.remove();
					System.out.println("there was a gc, remove -> " + forceGet(reference));
				} catch (InterruptedException e) {
					return;
				}
			}
		}.start();

		String o = new String("phantom");
		PhantomReference<String> reference = new PhantomReference<String>(o, queue);

		//虚引用 get 永远返回NULL
		System.out.println("reference.get() -> " + reference.get());
		//直接读取引用字段
		System.out.println("reference.referent -> " + forceGet(reference));

		Thread.sleep(2000);
		o = null;
		System.gc();
	}

	/**
	 * 暴力获得Reference#referent字段
	 * @param reference 引用
	 * @return 引用对象
	 */
	private static String forceGet(Reference<String> reference) {
		try {
			Field field = Reference.class.getDeclaredField("referent");
			field.setAccessible(true);
			return (String) field.get(reference);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
