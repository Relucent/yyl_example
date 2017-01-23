package yyl.example.demo.protobuff;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SerializeUtilTest {

	public static class A {
		public String name = "A";
		public List<String> strings = new ArrayList<String>();
		public Object[] o = { "A", new Integer(0) };

		public A() {
			strings.add("1");
			strings.add("2");
			strings.add("3");
		}
	}

	public static class F {
		final int value;

		public F(int v) {
			value = v;
		}

		@Override
		public String toString() {
			return value + "";
		}
	}

	public static class Wrap {
		public List<Object> args = new ArrayList<Object>();
	}

	public static void main(String[] args) {

		Wrap wrap = new Wrap();
		wrap.args.add(new A());
		wrap.args.add(Boolean.class);
		wrap.args.add(Boolean.TRUE);
		wrap.args.add(new BigDecimal("123.456"));
		wrap.args.add(new Integer(123));
		wrap.args.add(new F(5));
		wrap.args.add(null);

		byte[] data = SerializeUtil.encode(wrap);
		System.out.println(data.length);

		for (Object arg : SerializeUtil.decode(data, Wrap.class).args) {
			if (arg != null) {
				System.out.println(arg.getClass());
				System.out.println(arg);
			} else {
				System.out.println("NULL");
				System.out.println(arg);
			}

		}

	}
}
