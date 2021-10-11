package yyl.example.basic.jdk8.stream;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * {@link Stream}#collectingAndThen，先进行结果集的收集，然后将收集到的结果集进行下一步的处理。<br>
 */
public class StreamCollectingAndThenDemo {
    public static void main(String[] args) {

        List<Item> original = new ArrayList<>();
        original.add(create("A", 1));
        original.add(create("A", 2));
        original.add(create("A", 3));
        original.add(create("B", 1));
        original.add(create("B", 2));
        original.add(create("B", 3));
        original.add(create("C", 1));
        original.add(create("C", 2));
        original.add(create("C", 3));

        // 根据对象的 name 属性进行去重操作
        List<Item> result = original.stream().collect(Collectors.collectingAndThen(//
                Collectors.toCollection(() -> new TreeSet<Item>(Comparator.comparing(item -> item.name))), //
                ArrayList::new));
        result.forEach(System.out::println);
    }

    private static Item create(String name, Integer value) {
        Item item = new Item();
        item.name = name;
        item.value = value;
        return item;
    }

    private static class Item {
        private String name;
        private Integer value;

        @Override
        public String toString() {
            return "{name=" + name + ", value=" + value + "}";
        }
    }
}
