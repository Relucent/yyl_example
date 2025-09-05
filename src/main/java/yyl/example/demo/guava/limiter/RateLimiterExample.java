package yyl.example.demo.guava.limiter;

import java.time.LocalTime;

import com.google.common.util.concurrent.RateLimiter;

/**
 * 限流（Rate Limiting），使用令牌桶算法
 */
public class RateLimiterExample {
    public static void main(String[] args) {
        // 每秒 5 个许可
        RateLimiter limiter = RateLimiter.create(5.0);
        for (int i = 0; i < 100; i++) {
            // 阻塞直到获取到许可
            limiter.acquire();
            System.out.println((LocalTime.now()) + ":" + i);
        }
    }
}
