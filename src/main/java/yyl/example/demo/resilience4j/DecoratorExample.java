package yyl.example.demo.resilience4j;

import java.time.Duration;
import java.util.function.Supplier;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;

/**
 * 限流装饰器
 */
public class DecoratorExample {

    public static void main(String[] args) {

        RateLimiter limiter = RateLimiter.of("demo", RateLimiterConfig.custom()//
                .limitForPeriod(2) // 每周期允许 2 次
                .limitRefreshPeriod(Duration.ofSeconds(1)) // 周期 1 秒
                .timeoutDuration(Duration.ZERO) // 超过的阻塞时间（设置成0，表示不阻塞）
                .build());

        Supplier<String> helloSupplier = RateLimiter.decorateSupplier(limiter, () -> "hello");

        for (int i = 0; i < 5; i++) {
            try {
                System.out.println(helloSupplier.get());
            } catch (Exception e) {
                System.out.println("rate-limited");
            }
        }
    }
}
