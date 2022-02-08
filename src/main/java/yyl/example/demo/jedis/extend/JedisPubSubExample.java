package yyl.example.demo.jedis.extend;

import java.util.concurrent.CompletableFuture;

import redis.clients.jedis.JedisPubSub;

/**
 * _Redis 发布/订阅(publish/subscribe)
 */
public class JedisPubSubExample {

    public static void main(String[] args) throws Exception {

        try (JedisDS ds = JedisDS.builder().setHost("127.0.0.1").setPort(6379).build()) {

            JedisPubSub pubSub = new JedisPubSub() {
                @Override
                public void onMessage(String channel, String message) {
                    System.out.println(channel + "->" + message);
                    if ("exit()".equals(message)) {
                        this.unsubscribe();
                    }
                }
            };
            String channel = "my_channel";

            CompletableFuture<Void> subscribeFuture = CompletableFuture.runAsync(() -> {
                ds.execute(jedis -> {
                    System.out.println("subscribe()");
                    jedis.subscribe(pubSub, channel);
                    System.out.println("unsubscribe");
                });
            });
            Thread.sleep(1000);

            CompletableFuture<Void> publishFuture = CompletableFuture.runAsync(() -> {
                for (int i = 0; i < 10; i++) {
                    String message = String.valueOf(i);
                    ds.execute(jedis -> {
                        System.out.println(channel + "<-" + message);
                        jedis.publish(channel, message);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    });
                }
                ds.execute(jedis -> {
                    jedis.publish(channel, "exit()");
                });
            });

            CompletableFuture.allOf(subscribeFuture, publishFuture).get();
        }
    }
}
