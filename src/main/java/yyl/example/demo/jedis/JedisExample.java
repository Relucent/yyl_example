package yyl.example.demo.jedis;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import redis.clients.jedis.Jedis;

/**
 * Jedis 基本用法
 */
public class JedisExample {

    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost", 6379);
        // jedis.auth("admin");

        testInfo(jedis);
        testString(jedis);
        testHash(jedis);
        testList(jedis);
        testSet(jedis);
        testZSet(jedis);

        testCommands(jedis);

        jedis.disconnect();
    }

    /** 服务器的各种信息和统计数值 */
    private static void testInfo(Jedis jedis) {
        System.out.println("Info:\n");
        Properties properties = new Properties();
        try (Reader reader = new StringReader(jedis.info())) {
            properties.load(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            System.out.println(entry.getKey() + ":\t" + entry.getValue());
        }
    }

    /** 字符串 */
    private static void testString(Jedis jedis) {

        System.out.println("#string:");

        // # SET key "hello"
        System.out.println("> SET key \"hello\"");
        System.out.println(jedis.set("key", "hello"));

        // # GET key
        System.out.println("> GET key");
        System.out.println(jedis.get("key"));

        System.out.println("> APPEND key \" world\"");
        System.out.println(jedis.append("key", " world")); // 拼接
        System.out.println("> GET key");
        System.out.println(jedis.get("key"));

        // 删除某个键 #DEL key [key ...]
        System.out.println("> DEL key");
        System.out.println(jedis.del("key")); // 删除某个键
        System.out.println("> GET key");
        System.out.println(jedis.get("key"));

        // 设置多个键值
        // #MSET key1 "Hello" key2 "World"
        System.out.println("> MSET key1 \"Hello\" key2 \"World\"");
        System.out.println(jedis.mset("key1", "hello", "key2", "World"));

        System.out.println("> GET key1");
        System.out.println(jedis.get("key1"));
        System.out.println("> GET key2");
        System.out.println(jedis.get("key2"));

        // 删除一组键 #DEL key1 key2
        System.out.println("> DEL key1 key2");
        System.out.println(jedis.del("key1", "key2"));
        System.out.println(jedis.get("key2"));

        System.out.println("\n");
    }

    /** 哈希 */
    private static void testHash(Jedis jedis) {

        System.out.println("#hash:");

        System.out.println("> HMSET myhash field1 \"Hello\" field2 \"World\"");
        Map<String, String> map = new HashMap<String, String>();
        map.put("field1", "Hello");
        map.put("field2", "World");
        System.out.println(jedis.hmset("myhash", map));

        System.out.println("> HMGET myhash field1 field2");
        System.out.println(jedis.hmget("myhash", "field1", "field2"));

        // 返回 key 指定的哈希集中所有的字段和值。
        System.out.println("> HGETALL myhash");
        System.out.println(jedis.hgetAll("myhash"));

        // 删除map中的某个键值
        System.out.println("> HDEL myhash field1");
        System.out.println(jedis.hdel("myhash", "field1"));
        System.out.println("> HDEL myhash field1");
        System.out.println(jedis.hdel("myhash", "field1"));

        // 返回 key 指定的哈希集包含的字段的数量
        System.out.println("> HLEN myhash");
        System.out.println(jedis.hlen("myhash"));

        // 设置 key 指定的哈希集中指定字段的值
        System.out.println("> HSET myhash field1 \"Hello\"");
        System.out.println(jedis.hset("myhash", "field1", "Hello"));
        System.out.println("> HGET myhash field1");
        System.out.println(jedis.hget("myhash", "field1"));

        // 返回指定的哈希集中所有字段的名字
        System.out.println("> HKEYS myhash");
        System.out.println(jedis.hkeys("myhash"));

        // 移除哈希
        jedis.del("myhash");
        System.out.println("\n");
    }

    /** 列表 */
    private static void testList(Jedis jedis) {

        System.out.println("#list:");

        // 将所有指定的值插入到存于 key 的列表的头部
        // 如果 key 不存在，那么在进行 push 操作前会创建一个空列表
        // 如果 key 对应的值不是一个 list 的话，那么会抛出异常
        System.out.println("> HKEYS myhash");
        System.out.println(jedis.lpush("mylist", "hello"));
        System.out.println(jedis.lpush("mylist", "world"));

        // 返回存储在列表里指定范围内的元素
        // (偏移量也可以是负数，表示偏移量是从list尾部开始计数。 例如， -1 表示列表的最后一个元素，-2 是倒数第二个，以此类推)
        System.out.println(">  LRANGE mylist 0 -1");
        System.out.println(jedis.lrange("mylist", 0, -1));

        System.out.println("> DEL mylist");
        System.out.println(jedis.del("mylist"));

        // 向存于 key 的列表的尾部插入所有指定的值
        System.out.println("> RPUSH mylist \"1\" \"2\" \"3\" \"4\" \"5\"");
        System.out.println(jedis.rpush("mylist", "1", "2", "3", "4", "5"));

        // 移除并且返回list的第一个元素
        System.out.println("> LPOP mylist");
        System.out.println(jedis.lpop("mylist"));
        System.out.println("> LPOP mylist");
        System.out.println(jedis.lpop("mylist"));

        // 返回存储在 key 里的list的长度
        System.out.println("> LLEN mylist");
        System.out.println(jedis.llen("mylist"));

        // 移除列表
        jedis.del("mylist");
        System.out.println("\n");
    }

    /** 集合 */
    private static void testSet(Jedis jedis) {

        System.out.println("#set:");

        // 添加元素
        System.out.println("> SADD myset \"Hello\"");
        System.out.println(jedis.sadd("myset", "Hello"));
        System.out.println("> SADD myset \"World\"");
        System.out.println(jedis.sadd("myset", "World"));
        System.out.println("> SADD myset \"World\"");
        System.out.println(jedis.sadd("myset", "World"));

        // 获取集合成员个数
        System.out.println(jedis.scard("myset"));

        // 获取集合成员(元素)
        System.out.println("> SMEMBERS myset");
        System.out.println(jedis.smembers("myset"));

        // 移除集合
        jedis.del("myset");
        System.out.println("\n");
    }

    /** 有序集合 */
    private static void testZSet(Jedis jedis) {
        System.out.println("#zset:");

        System.out.println("> ZADD myzset 3 \"three\"");
        System.out.println(jedis.zadd("myzset", 3, "three"));

        System.out.println("> ZADD myzset 1 \"two\"");
        System.out.println(jedis.zadd("myzset", 2, "two"));

        System.out.println("> ZADD myzset 1 \"one\" 1 \"uno\"");
        Map<String, Double> scoreMembers = new HashMap<>();
        scoreMembers.put("one", 1D);
        scoreMembers.put("uno", 1D);
        System.out.println(jedis.zadd("myzset", scoreMembers));

        System.out.println("> ZRANGE myzset 0 -1");
        System.out.println(jedis.zrange("myzset", 0, -1));

        // 移除集合
        jedis.del("myzset");
        System.out.println("\n");
    }

    /** 其他常用操作 */
    private static void testCommands(Jedis jedis) {

        System.out.println("#COMMANDS:");

        // # 返回key所存储的value的数据结构类型
        System.out.println("> SET key1 \"value\"");
        System.out.println(jedis.set("key1", "value"));
        System.out.println("> HSET key2 field \"value\"");
        System.out.println(jedis.hset("key2", "field", "value"));
        System.out.println("> LPUSH key3 \"value\"");
        System.out.println(jedis.lpush("key3", "value"));
        System.out.println("> SADD key4 \"value\"");
        System.out.println(jedis.sadd("key4", "value"));
        System.out.println("> ZADD key5 \"value\"");
        System.out.println(jedis.zadd("key5", 0, "value"));

        // type可以返回string, list, set, zset , hash, none 等不同的类型。
        System.out.println("> TYPE key1");
        System.out.println(jedis.type("key1"));
        System.out.println("> TYPE key2");
        System.out.println(jedis.type("key2"));
        System.out.println("> TYPE key3");
        System.out.println(jedis.type("key3"));
        System.out.println("> TYPE key4");
        System.out.println(jedis.type("key4"));
        System.out.println("> TYPE key5");
        System.out.println(jedis.type("key5"));
        System.out.println("> TYPE key6");
        System.out.println(jedis.type("key6"));// 不存在时返回none

        System.out.println("\n");
        // 设置超时时间
        System.out.println("> SET mykey \"hello\"");
        System.out.println(jedis.set("mykey", "hello"));

        // 设置超时时间(单位：秒)
        System.out.println("> EXPIRE mykey 2 ");
        System.out.println(jedis.expire("mykey", 2));// [EXPIRE key seconds]

        // 返回key剩余的过期时间(单位：秒)
        System.out.println("> TTL mykey ");
        System.out.println(jedis.ttl("mykey"));

        // 返回key剩余的过期时间(单位：毫秒)
        System.out.println("> PTTL mykey ");
        System.out.println(jedis.pttl("mykey"));

        System.out.println("> EXISTS mykey ");
        System.out.println(jedis.exists("mykey"));

        System.out.println("# sleep 2000 Millisecond!");
        try {
            Thread.sleep(2 * 1000);
        } catch (InterruptedException e) {
        }

        System.out.println("> EXISTS mykey ");
        System.out.println(jedis.exists("mykey"));

        // 移除
        jedis.del("mykey", "key1", "key2", "key3", "key4", "key5");
        System.out.println("\n");
    }
}
