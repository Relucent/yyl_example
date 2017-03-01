package yyl.example.demo.guava.cache;

import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class CacheTest {

	public static void main(String[] args) throws Exception {
		Cache<String, String> cache = CacheBuilder.newBuilder()//
				.expireAfterAccess(10, TimeUnit.MILLISECONDS)//
				.build();

		for (int i = 0; i < 10; i++) {
			cache.put("key" + i, "value" + i);
		}

		System.out.println(cache.getIfPresent("key1"));

		TimeUnit.MILLISECONDS.sleep(20);

		System.out.println(cache.getIfPresent("key1"));
	}
}
