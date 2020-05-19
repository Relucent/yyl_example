package yyl.example.demo.mybatis;

import java.lang.reflect.InvocationTargetException;

import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaClass;
import org.apache.ibatis.reflection.invoker.GetFieldInvoker;
import org.apache.ibatis.reflection.invoker.SetFieldInvoker;

public class MetaClassExample {
	public static void main(String[] args) throws IllegalAccessException, InvocationTargetException {
		MetaClass metaClass = MetaClass.forClass(PrivateBean.class, new DefaultReflectorFactory());
		SetFieldInvoker setInvoker = (SetFieldInvoker) metaClass.getSetInvoker("value");
		GetFieldInvoker getInvoker = (GetFieldInvoker) metaClass.getGetInvoker("value");
		PrivateBean target = new PrivateBean();
		System.out.println(setInvoker.invoke(target, new Object[] { "hello" }));
		System.out.println(getInvoker.invoke(target, new Object[] {}));
	}
}
