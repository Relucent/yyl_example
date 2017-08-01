package yyl.example.basic.jdk8.stream;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * 这段代码演示了Stream的一些常用方法<br>
 * 这段代码的含义： 给定一个Integer类型的List，获取其对应的Stream对象，然后进行过滤掉null，再去重，再每个元素乘以2，再每个元素被消费的时候打印自身，跳过前两个元素，去掉最后去前四个元素进行加和运算<br>
 */
public class StreamForDemo {
	public static void main(String[] args) {
		List<Integer> data = Lists.newArrayList(1, 1, null, 2, null, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10);
		data.stream()//
				.filter(num -> num != null)//
				.distinct()//
				.mapToInt(num -> num * 2)//
				.peek(System.out::println)//
				.sorted()//
				.skip(2)//
				.limit(4)//
				.count();
	}

}
