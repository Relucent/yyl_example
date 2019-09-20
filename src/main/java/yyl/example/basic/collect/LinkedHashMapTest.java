package yyl.example.basic.collect;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * LinkedHashMap是HashMap的子类，但是内部还有一个双向链表维护键值对的顺序，每个键值对既位于哈希表中，也位于双向链表中。<br>
 * LinkedHashMap支持两种顺序插入顺序 、 访问顺序。<br>
 * 插入顺序：先添加的在前面，后添加的在后面。修改操作不影响顺序。<br>
 * 访问顺序：所谓访问指的是get/put操作，对一个键执行get/put操作后，其对应的键值对会移动到链表末尾，所以最末尾的是最近访问的，最开始的是最久没有被访问的，这就是访问顺序。<br>
 * LinkedHashMap有5个构造方法，其中4个都是按插入顺序，只有一个是可以指定按访问顺序：<br>
 * public LinkedHashMap(int initialCapacity, float loadFactor, boolean accessOrder) <br>
 * 利用 LinkedHashMap 的特性可以实现简单的 LRU缓存。<br>
 */
public class LinkedHashMapTest {

    private static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; // aka 16
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    public static void main(String[] args) {
        // 默认情况下，LinkedHashMap是按照插入顺序的
        System.out.println("Test Sequence Map");
        Map<String, Integer> sequenceMap = new LinkedHashMap<>();
        sequenceMap.put("c", 1);
        System.out.println(sequenceMap);
        sequenceMap.put("d", 2);
        System.out.println(sequenceMap);
        sequenceMap.put("b", 3);
        System.out.println(sequenceMap);
        sequenceMap.put("e", 4);
        System.out.println(sequenceMap);
        sequenceMap.put("a", 5);
        System.out.println(sequenceMap);
        System.out.println();

        // 按访问顺序
        System.out.println("Test Access Map");
        Map<String, Integer> accessMap = new LinkedHashMap<>(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR, true);
        accessMap.put("c", 1);
        System.out.println(accessMap);
        accessMap.put("d", 2);
        System.out.println(accessMap);
        accessMap.put("b", 3);
        System.out.println(accessMap);
        accessMap.put("e", 4);
        System.out.println(accessMap);
        accessMap.put("a", 5);
        System.out.println(accessMap);
        System.out.println("Get d");
        accessMap.get("d");// 访问的，最后访问的会被排到最后
        System.out.println(accessMap);
        System.out.println();


        // 基于按访问顺序的LRU缓存
        System.out.println("Test LRU Map");
        Map<String, Integer> lruMap = new LRUCache<>(3);
        lruMap.put("c", 1);
        System.out.println(lruMap);
        lruMap.put("d", 2);
        System.out.println(lruMap);
        lruMap.get("c");
        System.out.println(lruMap);
        lruMap.put("b", 3);
        System.out.println(lruMap);
        lruMap.put("e", 4);
        System.out.println(lruMap);
        lruMap.put("a", 5);
        System.out.println(lruMap);
    }


    /**
     * 使用 LinkedHashMap 实现缓存
     * @param <K> 键类型
     * @param <V> 值类型
     */
    @SuppressWarnings("serial")
    private static class LRUCache<K, V> extends LinkedHashMap<K, V> {

        private int maxEntries;

        public LRUCache(int maxEntries) {
            super(16, 0.75f, true);
            this.maxEntries = maxEntries;
        }

        /**
         * 在LinkedHashMap添加元素后，会调用removeEldestEntry方法，传递的参数时最久没有被访问的键值对，如果方法返回true，这个最久的键值对就会被删除。<br>
         * LinkedHashMap中的实现总返回false，该子类重写后即可实现对容量的控制。<br>
         * @param eldest 最老的元素
         * @return 如果方法返回true，这个最久的键值对就会被删除，如果返回false，不做任何处理
         */
        @Override
        protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
            System.out.println("eldest->" + eldest.getKey());
            return size() > maxEntries;
        }
    }
}
