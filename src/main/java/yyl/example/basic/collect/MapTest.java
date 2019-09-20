package yyl.example.basic.collect;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * HashMap 键是无序的，故需要对键值排序，则我们可以使用LinkedHashMap 或者 TreeMap。<br>
 * TreeMap 会按照键的自然顺序进行排序。<br>
 * LinkedHashMap 是 HashMap的子类，但是内部还有一个双向链表维护键值对的顺序，每个键值对既位于哈希表中，也位于双向链表中。<br>
 * LinkedHashMap 支持两种顺序插入顺序 、 访问顺序。<br>
 * HashMap、LinkedHashMap 和 TreeMap 都不是线程安全的， 在并发场景下可以使用ConcurrentMap。<br>
 * 在JDK1.8中，ConcurrentHashMap的性能和存储空间要优于ConcurrentSkipListMap。<br>
 * ConcurrentSkipListMap 会按照键的自然顺序进行排序。<br>
 */
public class MapTest {

    private static final Object PRESENT = new Object();

    public static void main(String[] args) {
        test(new HashMap<>()); // KEY 无序
        test(new LinkedHashMap<>());// KEY 按照插入顺序排序
        test(new TreeMap<>());// KEY 自动排序
        test(new ConcurrentSkipListMap<>());// KEY 自动排序
    }

    private static void test(Map<String, Object> map) {
        map.put("D1", PRESENT);
        map.put("E2", PRESENT);
        map.put("B3", PRESENT);
        map.put("C4", PRESENT);
        map.put("A5", PRESENT);
        System.out.println(map.getClass());
        for (String key : map.keySet()) {
            System.out.println(key);
        }
    }
}
