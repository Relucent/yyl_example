package yyl.example.demo.guava.collect;

import java.util.Arrays;
import java.util.Comparator;

import com.google.common.collect.ComparisonChain;

/** 多字段排序比较器 */
public class ComparisonChainTest {

	public static void main(String[] args) {
		Foo[] array = { //
				new Foo(1, 2, 3), //
				new Foo(3, 2, 1), //
				new Foo(1, 1, 3), //
				new Foo(2, 1, 3), //
				new Foo(2, 3, 1), //
				new Foo(3, 1, 2)//
		};//

		Arrays.sort(array, new Comparator<Foo>() {
			@Override
			public int compare(Foo f1, Foo f2) {
				return ComparisonChain.start() //
						.compare(f1.a, f2.a) //
						.compare(f1.b, f2.b) //
						.compare(f1.c, f2.c)//
						.result();
			}
		});

		for (Foo foo : array) {
			System.out.println(foo);
		}

	}

	//
	//	//	ComparisonChain

	static class Foo {

		int a;
		int b;
		int c;

		public Foo(int a, int b, int c) {
			this.a = a;
			this.b = b;
			this.c = c;
		}

		@Override
		public String toString() {
			return "Foo [a=" + a + ", b=" + b + ", c=" + c + "]";
		}
	}

}
