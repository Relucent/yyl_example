package yyl.example.basic.enumtest;

public class EnumTest {

	static enum e {
		A, B, C, D, E, F, G
	};

	public static void main(String[] args) {
		for (e c : e.values()) {
			System.out.println(c.ordinal() + "|" + c.toString() + "|" + c.getClass().getName());
		}
	}

}
