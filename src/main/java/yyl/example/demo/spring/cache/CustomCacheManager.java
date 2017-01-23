package yyl.example.demo.spring.cache;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

/**
 * 自定义缓存管理器例子
 */
public class CustomCacheManager implements CacheManager {

	// ==============================Fields===========================================
	private final ConcurrentMap<String, Cache> cacheMap;

	// ==============================Constructors=====================================
	public CustomCacheManager() {
		cacheMap = new ConcurrentHashMap<String, Cache>(16);
	}

	// ==============================Methods==========================================
	@Override
	public Collection<String> getCacheNames() {
		return Collections.unmodifiableSet(this.cacheMap.keySet());
	}

	@Override
	public Cache getCache(String name) {
		Cache cache = this.cacheMap.get(name);
		if (cache == null) {
			synchronized (this.cacheMap) {
				cache = this.cacheMap.get(name);
				if (cache == null) {
					cache = createGuavaCache(name);
					this.cacheMap.put(name, cache);
				}
			}
		}
		return cache;
	}

	protected Cache createGuavaCache(String name) {
		return new CustomCache(name);
	}
}
