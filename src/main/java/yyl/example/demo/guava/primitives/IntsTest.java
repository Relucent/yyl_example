package yyl.example.demo.guava.primitives;

import java.util.List;

import com.google.common.primitives.Ints;

public class IntsTest {

	public static void main(String[] args) {
		List<Integer> intList = Ints.asList(1, 2, 3, 4, 5);
		int[] ints = Ints.toArray(intList);

		System.out.println("min-> " + Ints.min(ints));
		System.out.println("max-> " + Ints.max(ints));

	}

}
