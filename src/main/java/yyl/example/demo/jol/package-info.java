package yyl.example.demo.jol;
/**
 * JOL全称为Java Object Layout，是分析JVM中对象布局的工具。该工具大量使用了Unsafe、JVMTI来解码布局情况，所以分析结果是比较精准的。<br>
 * 
 * <pre>
 * 对象 
 * ├ 对象头  (标记字段 + 类指针) 
 * ├ 实例数据 
 * └ padding填充字节
 * </pre>
 * 
 * 1、对象头： 在jvm虚拟机中每一个java对象都有一个对象头，对象头中包含标记字段（markword）以及对象指针，标记字段用来储存hash码、gc信息以及锁信息，而指针则指向改对象的类。<br>
 * 在64位jvm虚拟机中这两部分都是64位的，所以也就是需要128位大小（16 bytes）。<br>
 * 注意：64位虚拟机中在堆内存小于32GB的情况下，UseCompressedOops是默认开启的，该参数表示开启指针压缩，会将原来64位的指针压缩为32位。<br>
 * 2、实例数据： 类中所有的实例字段数据<br>
 * 3、内存填充部分（alignment）： （虚拟机规范要求对象所占内存必须是8的倍数）构成该部分作用是用来保证java对象在虚拟机中占内存大小为8N bytes。<br>
 * <br>
 * 
 * <pre>
 * java的基础数据类型所占内存情况如下：
 * 	boolean	：	1 bytes
 * 	byte	：	1 bytes
 * 	short	：	2 bytes
 * 	char	：	2 bytes
 * 	int		：	4 bytes
 * 	long	：	8 bytes
 * 	float	： 	4 bytes
 * 	double	：	8 bytes
 * </pre>
 */
