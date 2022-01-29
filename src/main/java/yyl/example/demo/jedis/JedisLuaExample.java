package yyl.example.demo.jedis;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import redis.clients.jedis.Jedis;

/**
 * 使用jedis执行lua脚本
 */
public class JedisLuaExample {

    public static void main(String[] args) throws InterruptedException {
        Jedis jedis = new Jedis("localhost", 6379);
        String key = "test-key";
        set(jedis, key, "test-value", 5000L);
        Object value = get(jedis, key);
        System.out.println(value);
        Object ttl = ttl(jedis, key);
        System.out.println(ttl);
        jedis.close();
    }

    private static void set(Jedis jedis, String key, String value, long pexpire) {
        final String script = "" + //
                " redis.call('set', KEYS[1], ARGV[1]);" + //
                " redis.call('pexpire', KEYS[1], ARGV[2]);";//
        final List<String> keys = Collections.singletonList(key);
        final List<String> args = Arrays.asList(value, String.valueOf(pexpire));
        jedis.eval(script, keys, args);
    }

    private static Object get(Jedis jedis, String key) {
        final String script = "return redis.call('get', KEYS[1]);";
        final List<String> keys = Collections.singletonList(key);
        final List<String> args = Arrays.asList();
        return jedis.eval(script, keys, args);
    }

    private static Object ttl(Jedis jedis, String key) {
        final String script = "return redis.call('ttl', KEYS[1]);";
        final List<String> keys = Collections.singletonList(key);
        final List<String> args = Arrays.asList();
        return jedis.eval(script, keys, args);
    }
}
