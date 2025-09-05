package yyl.example.demo.resilience4j;

import java.time.Duration;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;

/**
 * 限流示例
 */
public class RatelimiterExample {

    public static void main(String[] args) {

        RateLimiterConfig config = RateLimiterConfig.custom()//
                .limitForPeriod(2) // 每周期 2 次
                .limitRefreshPeriod(Duration.ofSeconds(1)) // 周期 1 秒
                .timeoutDuration(Duration.ZERO) // 不等待
                .build();

        RateLimiter limiter = RateLimiter.of("helloLimiter", config);

        for (int i = 1; i <= 5; i++) {
            if (limiter.acquirePermission()) {
                System.out.println(i + " -> hello");
            } else {
                System.out.println(i + " -> rate-limited");
            }
        }
    }
}
