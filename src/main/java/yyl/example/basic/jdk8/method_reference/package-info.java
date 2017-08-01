/**
 * 方法引用 (Method References)<br>
 * 通常与Lambda表达式联合使用，可以直接引用已有Java类或对象的方法。<br>
 * 一般有四种不同的方法引用：<br>
 * 构造器引用。语法是Class::new，或者更一般的Class< T >::new，要求构造器方法是没有参数；<br>
 * 静态方法引用。语法是Class::static_method，要求接受一个Class类型的参数；<br>
 * 特定类的任意对象方法引用。它的语法是Class::method。要求方法是没有参数的；<br>
 * 特定对象的方法引用，它的语法是instance::method。要求方法接受一个参数，与3不同的地方在于，3是在列表元素上分别调用方法，而4是在某个对象上调用方法，将列表元素作为参数传入；<br>
 */
package yyl.example.basic.jdk8.method_reference;
