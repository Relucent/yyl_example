package yyl.example.demo.redisson;

import org.redisson.Redisson;
import org.redisson.api.RList;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

public class RedissonExample {
    public static void main(String[] args) {
        Config config = new Config();
        config.useSingleServer()//
                .setAddress("redis://localhost:6379")//
                .setDatabase(0);
        RedissonClient client = Redisson.create(config);
        RList<Object> list = client.getList("test:list");
        list.add("A");
        list.add("B");
        list.add("C");
        System.out.println("size:" + list.size());
        for (Object element : list) {
            System.out.println(element);
        }
        list.delete();
        client.shutdown();
    }
}
