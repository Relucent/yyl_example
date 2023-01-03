package yyl.example.demo.caffeine;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

/**
 * Caffeine是一种高性能的缓存库，是基于Java 8的缓存框架。<br>
 * Caffeine提供了灵活的结构来创建缓存，并且有以下特性：<br>
 * 1、自动加载条目到缓存中，可选异步方式<br>
 * 2、可以基于大小剔除<br>
 * 3、可以设置过期时间，时间可以从上次访问或上次写入开始计算<br>
 * 4、异步刷新<br>
 * 5、keys自动包装在弱引用中<br>
 * 6、values自动包装在弱引用或软引用中<br>
 * 7、条目剔除通知<br>
 * 8、缓存访问统计<br>
 */
public class CaffeineExample {

    public static void main(String[] args) throws InterruptedException {
        Cache<String, Object> cache = Caffeine.newBuilder()//
                .expireAfterAccess(1, TimeUnit.SECONDS)// 1秒没有读写自动删除
                .maximumSize(5)// 只保留5个
                .removalListener((key, value, cause) -> {
                    // 清理通知 key,value ==> 键值对 cause ==> 清理原因
                    System.out.println("- " + key + ":" + value + " " + cause);
                }).build();

        List<String> keys = new ArrayList<>();

        System.out.println("#PUT");
        for (int i = 0; i < 10; i++) {
            String key = "key" + i;
            Object value = "value" + i;
            keys.add(key);
            cache.put(key, value);
            System.out.println("+ " + key + ":" + value);
        }

        System.out.println("\n#GET");
        println(cache, keys);

        System.out.println("\n#SLEEP 20MS");
        TimeUnit.MILLISECONDS.sleep(20);
        System.out.println("\n#GET");
        println(cache, keys);

        System.out.println("\n#SLEEP 2S");
        TimeUnit.MILLISECONDS.sleep(2000);
        System.out.println("\n#GET");
        println(cache, keys);
    }

    private static void println(Cache<String, Object> cache, List<String> keys) {
        for (String key : keys) {
            System.out.println("= " + key + ":" + cache.getIfPresent(key));
        }
    }
}
