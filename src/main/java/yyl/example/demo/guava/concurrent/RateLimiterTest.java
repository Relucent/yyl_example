package yyl.example.demo.guava.concurrent;

import com.google.common.util.concurrent.RateLimiter;

/**
 * RateLimiter : 速率限制器<br>
 * 速率限制器会在可配置的速率下分配许可证。如果必要的话，每个acquire() 会阻塞当前线程直到许可证可用后获取该许可证。<br>
 * 获取到许可证，不需要再释放许可证。<br>
 */
public class RateLimiterTest {
	public static void main(String[] args) {

		//根据指定的稳定吞吐率创建RateLimiter，这里的吞吐率是指每秒多少许可数
		final RateLimiter rateLimiter = RateLimiter.create(2.0);//速率是每秒2个许可

		for (int i = 0; i < 30; i++) {
			rateLimiter.acquire(); //从RateLimiter获取一个许可，该方法会被阻塞直到获取到请求
			System.out.println((System.currentTimeMillis() / 1000) + "->" + (i));
		}
	}
}
