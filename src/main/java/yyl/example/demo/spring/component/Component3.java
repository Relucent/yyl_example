package yyl.example.demo.spring.component;

import java.util.UUID;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * Spring缓存测试<br>
 */
@Component
public class Component3 {

	/**
	 * 获得一个随机字符串
	 * @param id ID
	 * @return 如果缓存生效，那么的第二次调用会走缓存，否则多次调用的结果是不一致的，
	 */
	@Cacheable(value = "component3")
	public String getFromCache(String id) {
		return id + ":" + UUID.randomUUID().toString();
	}

}
