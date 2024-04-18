package yyl.example.demo.spring.cache;

import java.io.Serializable;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.util.Assert;

/**
 * 自定义缓存例子 (内部实现使用了ConcurrentMap)
 */
public class CustomCache implements Cache {
	// ==============================Fields===========================================
	private final String name;
	private final ConcurrentMap<Object, Object> store;
	private final boolean allowNullValues;

	// ==============================Constructors=====================================
	public CustomCache(String name) {
		this(name, new ConcurrentHashMap<Object, Object>(256), true);
	}

	public CustomCache(String name, boolean allowNullValues) {
		this(name, new ConcurrentHashMap<Object, Object>(256), allowNullValues);
	}

	public CustomCache(String name, ConcurrentMap<Object, Object> store, boolean allowNullValues) {
		Assert.notNull(name, "Name must not be null");
		Assert.notNull(store, "Store must not be null");
		this.name = name;
		this.store = store;
		this.allowNullValues = allowNullValues;
	}

	// ==============================Methods==========================================
	@Override
	public void evict(Object key) {
		this.store.remove(key);
	}

	@Override
	public void clear() {
		this.store.clear();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public final ConcurrentMap<Object, Object> getNativeCache() {
		return this.store;
	}

	@Override
	public ValueWrapper get(Object key) {
		Object value = lookup(key);
		return toValueWrapper(value);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(Object key, Class<T> type) {
		Object value = fromStoreValue(lookup(key));
		if (value != null && type != null && !type.isInstance(value)) {
			throw new IllegalStateException("Cached value is not of required type [" + type.getName() + "]: " + value);
		}
		return (T) value;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(Object key, Callable<T> valueLoader) {
		return (T) this.store.computeIfAbsent(key, k -> toStoreValue(valueLoader));
	}

	@Override
	public void put(Object key, Object value) {
		this.store.put(key, toStoreValue(value));
	}

	@Override
	public ValueWrapper putIfAbsent(Object key, Object value) {
		Object existing = this.store.putIfAbsent(key, toStoreValue(value));
		return toValueWrapper(existing);
	}

	// ==============================ToolMethods======================================
	protected Object lookup(Object key) {
		return this.store.get(key);
	}

	protected Object toStoreValue(Object userValue) {
		if (userValue == null) {
			return NullValue.INSTANCE;
		}
		return userValue;
	}

	protected Cache.ValueWrapper toValueWrapper(Object storeValue) {
		return (storeValue != null ? new SimpleValueWrapper(fromStoreValue(storeValue)) : null);
	}

	protected Object fromStoreValue(Object storeValue) {
		if (this.allowNullValues && storeValue == NullValue.INSTANCE) {
			return null;
		}
		return storeValue;
	}

	// ==============================InnerClass=======================================
	@SuppressWarnings("serial")
	public static final class NullValue implements Serializable {
		static final Object INSTANCE = new NullValue();

		private NullValue() {
		}

		private Object readResolve() {
			return INSTANCE;
		}
	}
}
