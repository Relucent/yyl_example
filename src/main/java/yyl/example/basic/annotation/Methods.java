package yyl.example.basic.annotation;

public class Methods {

	@Anno(id = 1)
	public void setA() {
		System.out.println("Set A");
	}

	public void setB() {
		System.out.println("Set B");
	}

	@Anno(id = 3, name = "Set C")
	public void setC() {
		System.out.println("Set C");
	}
}
