package yyl.example.demo.lettuce;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

/**
 * Lettuce 基础用法
 */
public class LettuceExample {
    public static void main(String[] args) {
        RedisClient client = RedisClient.create(RedisURI.create("redis://localhost/"));
        try (StatefulRedisConnection<String, String> conn = client.connect()) {
            RedisCommands<String, String> command = conn.sync();
            command.set("hello", "hello lettuce");
            String value = command.get("hello");
            System.out.println(value);
        }
    }
}
