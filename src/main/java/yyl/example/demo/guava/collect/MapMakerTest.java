package yyl.example.demo.guava.collect;

import java.util.concurrent.ConcurrentMap;

import com.google.common.collect.MapMaker;

public class MapMakerTest {
	public static void main(String[] args) throws InterruptedException {
		ConcurrentMap<Object, Object> map = new MapMaker()//
				.weakKeys() // 指定Map保存的Key为WeakReference机制  
				.makeMap();

		Object key = new Object();
		map.put(key, ""); // 加入元素  

		key = null; // key变成了WeakReference

		Thread.sleep(1 * 1000);
		System.gc();// 触发垃圾回收  

		// map空了，因为WeakReference被回收
		System.out.println(map);

		// 可能是 MapMaker的 BUG, 返回1
		System.out.println(map.size());

		// 使用迭代器 hasNext 判断空
		System.out.println(map.keySet().iterator().hasNext());
	}
}
