package yyl.example.basic.reference;

import java.util.WeakHashMap;

/**
 * 以弱键实现的基于哈希表的 Map，在 WeakHashMap中，当某个键不再存在强引用时，将自动移除其条目。<br>
 * 对于一个给定的键，其映射的存在并不阻止垃圾回收器对该键的丢弃；丢弃某个键时，其条目从映射中有效地移除。<br>
 */
public class WeakHashMapTest {

	public static void main(String[] args) throws InterruptedException {

		WeakHashMap<Object, Object> map = new WeakHashMap<Object, Object>();

		Object k1 = new Object();
		Object k2 = new Object();
		Object k3 = new Object();

		map.put(k1, new Value("v1"));
		map.put(k2, new Value("v2"));
		map.put(k3, new Value("v3"));

		System.out.println(map.size());

		k1 = k2 = null;//置NULL，本质是去到k1,k2的强引用

		System.gc();
		Thread.sleep(1000);
		//map.size()里面 会调用 map.expungeStaleEntries() 方法
		System.out.println(map.size());

		System.gc();
		Thread.sleep(1000);
	}

	private static class Value {
		String value;

		public Value(String value) {
			this.value = value;
		}

		//析构函数(只是为了监控垃圾回收，该方法正常应用应当尽量避免)
		@Override
		protected void finalize() throws Throwable {
			System.out.println(value + " -> finalize!");
		}
	}
}
