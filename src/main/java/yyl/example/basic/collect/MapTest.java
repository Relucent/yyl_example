package yyl.example.basic.collect;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentSkipListMap;

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
