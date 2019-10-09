package yyl.example.basic.jdk5;

import java.util.Arrays;
import java.util.List;

/**
 * forEach 语句是java 5的新特征之一，在遍历集合，数组方面提供了很大的便利。<br>
 * forEach 语句是for语句的语法糖，可以看做 for 语句的简化版本。<br>
 * forEach 语句并不能完全取代for语句，但是任何的forEach语句都可以改写为for语句版本。 <br>
 * for(元素数据类型 元素变量 : 遍历对象) { 循环体内容 }
 */
public class ForEachTest {

    public static void main(String[] args) {
        int[] arrays = {0, 1, 2};
        for (int value : arrays) {
            System.out.println(value); // 逐个输出数组元素的值
        }

        List<String> list = Arrays.asList("A", "B", "C");
        for (String value : list) {
            System.out.println(value); // 逐个输出数组元素的值
        }
    }
}
