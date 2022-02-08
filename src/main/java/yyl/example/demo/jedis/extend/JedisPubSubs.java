package yyl.example.demo.jedis.extend;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

/**
 * 统一公用的发布订阅处理(_Redis的订阅会造成阻断，占用一个连接)
 */
public class JedisPubSubs implements Closeable {

    private static final String DEFAULT_CHANNEL_PREFIX = "_yyl__channel:";

    private static final int NEW_STATE = 0;
    private static final int SUBSCRIBED_STATE = 1;
    private static final int TERMINATED_STATE = 2;

    private final JedisPool pool;
    private final String channelPrefix;
    private final String channelPattern;
    private final JedisPubSub pubSub;
    private final AtomicInteger state = new AtomicInteger(NEW_STATE);

    private final Map<String, List<Consumer<String>>> topicListeners = new ConcurrentHashMap<>();
    private final Object topicAccessLock = new byte[0];

    public JedisPubSubs(JedisPool pool) {
        this(pool, DEFAULT_CHANNEL_PREFIX);
    }

    public JedisPubSubs(JedisPool pool, String channelPrefix) {
        this.pool = pool;
        this.channelPrefix = channelPrefix;
        this.channelPattern = channelPrefix + "*";
        this.pubSub = new JedisPubSub() {
            @Override
            public void onPMessage(String pattern, String channel, String message) {
                if (!JedisPubSubs.this.channelPattern.equals(pattern)) {
                    return;
                }
                String topic = getTopicName(channel);
                if (topic == null) {
                    return;
                }
                synchronized (topicAccessLock) {
                    List<Consumer<String>> listeners = topicListeners.get(topic);
                    if (listeners != null && !listeners.isEmpty()) {
                        for (Consumer<String> listener : listeners) {
                            try {
                                listener.accept(message);
                            } catch (Exception e) {
                                if (e instanceof InterruptedException) {
                                    Thread.currentThread().interrupt();
                                }
                            }
                        }
                    }
                }
            }
        };
        init();
    }

    public void subscribe(String topic, Consumer<String> listener) {
        if (state.get() == NEW_STATE) {
            init();
        }
        if (state.get() == SUBSCRIBED_STATE) {
            synchronized (topicAccessLock) {
                topicListeners.computeIfAbsent(topic, k -> new ArrayList<>()).add(listener);
            }
        }
    }

    public void unsubscribe(String topic, Consumer<String> listener) {
        List<Consumer<String>> listeners = topicListeners.get(topic);
        if (listeners == null) {
            return;
        }
        synchronized (topicAccessLock) {
            listeners.remove(listener);
        }
    }

    public void publish(String topic, String message) {
        String channelName = getChannelName(topic);
        try (Jedis jedis = pool.getResource()) {
            jedis.publish(channelName, message);
        }
    }

    public String getChannelName(String topic) {
        return channelPrefix + topic;
    }

    private String getTopicName(String channel) {
        if (!channel.startsWith(channelPrefix)) {
            return null;
        }
        return channel.substring(JedisPubSubs.this.channelPrefix.length());
    }

    private void init() {
        if (state.compareAndSet(NEW_STATE, SUBSCRIBED_STATE)) {
            CompletableFuture.runAsync(() -> {
                try (Jedis jedis = pool.getResource()) {
                    try {
                        jedis.psubscribe(pubSub, channelPattern);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @Override
    public void close() {
        if (state.compareAndSet(SUBSCRIBED_STATE, TERMINATED_STATE)) {
            pubSub.unsubscribe();
        }
    }
}
