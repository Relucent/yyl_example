package yyl.example.basic.jdk8.annotations;

import java.lang.annotation.Repeatable;

@Repeatable(value = MyAnnotations.class)
public @interface MyAnnotation {
	String value();
}
