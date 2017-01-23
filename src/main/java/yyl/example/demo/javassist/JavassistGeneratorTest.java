package yyl.example.demo.javassist;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtField.Initializer;
import javassist.CtNewMethod;
import javassist.Modifier;
import javassist.NotFoundException;

/**
 * Javassist是一个开源的分析、编辑和创建Java字节码的类库。<br>
 * 是由东京工业大学的数学和计算机科学系的 Shigeru Chiba （千叶 滋）所创建的。<br>
 * 它已加入了开放源代码JBoss 应用服务器项目,通过使用Javassist对字节码操作为JBoss实现动态"AOP"框架。<br>
 * javassist是jboss的一个子项目，其主要的优点，在于简单，而且快速。 直接使用java编码的形式，而不需要了解虚拟机指令，就能动态改变类的结构，或者动态生成类。<br>
 */
public class JavassistGeneratorTest {

	public static void main(String[] args) throws CannotCompileException, NotFoundException, InstantiationException,
			IllegalAccessException, ClassNotFoundException, NoSuchMethodException, SecurityException,
			IllegalArgumentException, InvocationTargetException {

		//ClassPool是缓存CtClass对象的容器，所有的CtClass对象都在ClassPool中，为了避免内存的消耗，创建ClassPool对象时可以使用单例模式。
		//可以调用CtClass对的detach方法将其从ClassPool中移除。（但是这样的话CtClass就不可用了）
		ClassPool classPool = ClassPool.getDefault();

		//新增一个类定义
		CtClass ctClass = classPool.makeClass("yyl.TestClass");

		//定义类的字段
		CtField ctField = new CtField(classPool.get("java.lang.String"), "name", ctClass);
		//定义字段的修饰符号(这里定义为PRIVATE)
		ctField.setModifiers(Modifier.PRIVATE);
		//添加字段
		ctClass.addField(ctField, Initializer.constant(""));

		//添加私有成员name及其getter、setter方法
		ctClass.addMethod(CtNewMethod.setter("setName", ctField));
		ctClass.addMethod(CtNewMethod.getter("getName", ctField));

		//创建无参的构造体
		CtConstructor cons = new CtConstructor(new CtClass[] {}, ctClass);
		cons.setBody("{name= \"Adam\";}");
		ctClass.addConstructor(cons);

		// 添加有参的构造体
		cons = new CtConstructor(new CtClass[] { classPool.get("java.lang.String") }, ctClass);
		cons.setBody("{$0.name = $1;}");
		ctClass.addConstructor(cons);

		// 调用toClass() 方法时，会将类加载到内存中，类似的方法还有：
		// writeFile() 将class写入到文件中 toBytecode() 输出类的字节码
		// 当CtClass调用writeFile/toClass/toBytecode方法时，Javassist会冻结CtClass(修改将不允许)。
		// 这个主要是为了警告开发者该类已经被加载，而JVM是不允许重新加载该类的。
		// 如果要突破该限制，可以 调用 defrost方法

		// 加载类并打印类名
		System.out.println(ctClass.toClass());

		//#

		// 通过反射获得Class实例
		Class<?> testClass = Class.forName("yyl.TestClass");

		//创建无参的实例，并调用getName方法
		Object o = testClass.newInstance();
		Method getter = testClass.getMethod("getName");
		System.out.println(getter.invoke(o));

		// 调用其setName方法
		Method setter = testClass.getMethod("setName", new Class[] { String.class });
		setter.invoke(o, "Dante");
		System.out.println(getter.invoke(o));

		// 通过反射创建有参的实例，并调用getName方法
		o = Class.forName("yyl.TestClass").getConstructor(String.class).newInstance("Raphael");
		getter = testClass.getMethod("getName");
		System.out.println(getter.invoke(o));

	}

}