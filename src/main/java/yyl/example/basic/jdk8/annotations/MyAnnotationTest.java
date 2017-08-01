package yyl.example.basic.jdk8.annotations;

import java.lang.annotation.Annotation;
import java.util.Arrays;

public class MyAnnotationTest {

	@MyAnnotation("value1")
	@MyAnnotation("value2")
	@MyAnnotation("value3")
	public void method() {

	}

	public static void main(String[] args) throws Exception {
		Annotation[] annotations = MyAnnotationTest.class.getMethod("method").getAnnotations();
		System.out.println(annotations.length); //1  
		Arrays.stream(annotations).forEach(System.out::println);
	}
}
