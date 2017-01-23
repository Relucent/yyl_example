package yyl.example.basic.algorithm.test;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

//多线程并发查找
//这个查找顺序具有不确定性，当两个路径同时满足规则时，返回结果不确定(看那个路径先被查找到)
public class Test_05Finder03 {

	public static String findPath(Object o, String targetKey) {
		final AtomicReference<String> result = new AtomicReference<String>();
		final AtomicBoolean found = new AtomicBoolean(false);
		final AtomicInteger latch = new AtomicInteger(0);
		final ExecutorService executorService = Executors.newCachedThreadPool();
		class FindCallable implements Runnable {
			private final String path;
			private final Object value;
			private final String targetKey;

			public FindCallable(String path, Object value, String targetKey) {
				this.path = path;
				this.value = value;
				this.targetKey = targetKey;
				latch.incrementAndGet();
			}

			@Override
			public void run() {
				try {
					if (found.get()) {
						return;
					}
					if (!(value instanceof Map)) {
						return;
					}
					Map<?, ?> map = (Map<?, ?>) value;
					for (Map.Entry<?, ?> entry : map.entrySet()) {
						Object k = entry.getKey().toString();
						Object v = entry.getValue();
						String r = path + "." + k;
						if (k.equals(targetKey)) {
							synchronized (found) {
								if (!found.get()) {
									result.set(r.substring(1));
									found.set(true);
								}
							}
							return;
						} else {
							executorService.submit(new FindCallable(r, v, targetKey));
						}
					}
				} finally {
					latch.decrementAndGet();
				}
			}
		}
		executorService.submit(new FindCallable("", o, targetKey));
		while (latch.get() > 0)
			;
		executorService.shutdown();
		return result.get();
	}
}
