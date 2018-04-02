/**
 * JAva QUEry <br>
 * https://github.com/TrigerSoft/jaque <br>
 * 
 * This library enables Java 8 Lambdas to be represented as objects in the form of expression trees at runtime: <br>
 * 
 * <pre>
 * void method(Predicate<Customer> p) {
 * 	LambdaExpression<Predicate<Customer>> parsed = LambdaExpression.parse(p);
 * 	// Use parsed Expression Tree...
 * }
 * </pre>
 */
package yyl.example.demo.jaque;
//获得 JAVA 8 Lambda 运行时表达式树