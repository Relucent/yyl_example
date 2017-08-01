/**
 * 在Java 5中使用注解有一个限制，即相同的注解在同一位置只能声明一次。<br>
 * Java 8引入重复注解，这样相同的注解在同一地方也可以声明多次。<br>
 * 重复注解机制本身需要用@Repeatable注解。<br>
 * Java 8在编译器层做了优化，相同注解会以集合的方式保存，因此底层的原理并没有变化。<br>
 */
package yyl.example.basic.jdk8.annotations;