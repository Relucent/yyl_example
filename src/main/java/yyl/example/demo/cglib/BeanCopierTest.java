package yyl.example.demo.cglib;

import net.sf.cglib.beans.BeanCopier;

public class BeanCopierTest {
	public static void main(String[] args) {
		Bean1 bean1 = new Bean1();
		Bean2 bean2 = new Bean2();
		bean1.setValue("hello");
		BeanCopier copier = BeanCopier.create(Bean1.class, Bean2.class, false);
		copier.copy(bean1, bean2, null);

		System.out.println(bean2.getValue());
	}

	static class Bean1 {
		private String value;
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
	}
	static class Bean2 {
		private String value;
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
	}
}
