package yyl.example.demo.javassist;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.NotFoundException;

/**
 * 用 javassist 动态创建类的演示<br>
 */
public class CreateSimpleBeanTest {

	@SuppressWarnings("unchecked")
	public static Class<? extends SimpleBean> create(String className, String... fields) throws CannotCompileException, NotFoundException {
		ClassPool pool = ClassPool.getDefault();
		CtClass cc = pool.makeClass(className);

		cc.setInterfaces(new CtClass[] { pool.get(SimpleBean.class.getName()) });
		CtClass stringctClass = pool.get("java.lang.String");

		{
			for (String field : fields) {
				cc.addField(new CtField(stringctClass, field, cc));
			}
		}
		{
			CtMethod setCtMethod = new CtMethod(CtClass.voidType, "set", new CtClass[] { stringctClass, stringctClass }, cc);
			setCtMethod.setModifiers(Modifier.PUBLIC);
			StringBuilder vSetBody = new StringBuilder();
			vSetBody.append("if(null == $1){ throw new NullPointerException(\" key can not be NULL \"); }");
			for (String field : fields) {
				vSetBody.append("else if(\"").append(field).append("\".equals($1)){ $0.").append(field).append("=$2; }");
			}
			vSetBody.append("else { throw new IllegalArgumentException(\"Object does not exist in the \" + $1 + \" field\"); }");
			setCtMethod.setBody(vSetBody.toString());
			cc.addMethod(setCtMethod);
		}
		{
			CtMethod getCtMethod = new CtMethod(stringctClass, "get", new CtClass[] { stringctClass }, cc);
			getCtMethod.setModifiers(Modifier.PUBLIC);
			StringBuilder vGetBody = new StringBuilder();
			vGetBody.append("if(null == $1){ throw new NullPointerException(\" key can not be NULL \"); }");
			for (String field : fields) {
				vGetBody.append("else if(\"").append(field).append("\".equals($1)){ return $0.").append(field).append("; }");
			}
			vGetBody.append("else { throw new IllegalArgumentException(\"Object does not exist in the \" + $1 + \" field\"); }");
			getCtMethod.setBody(vGetBody.toString());
			cc.addMethod(getCtMethod);
		}
		{
			CtConstructor cons = new CtConstructor(new CtClass[] {}, cc);
			cons.setBody("{}");
			cc.addConstructor(cons);
		}
		cc.detach();
		return cc.toClass();
	}

	public static interface SimpleBean {

		String get(String field);

		void set(String field, String value);
	}

	public static void main(String[] args) throws CannotCompileException, NotFoundException, InstantiationException, IllegalAccessException,
			NoSuchFieldException, SecurityException {
		Class<? extends SimpleBean> clazz = create("yyl.test.Test$" + System.currentTimeMillis(), //
				"v1", "v2", "v3", "v4", "v5");
		test0(clazz);
		test1(clazz);
		test2(clazz);
		test3();
	}

	private static void test0(Class<? extends SimpleBean> clazz) throws InstantiationException, IllegalAccessException {
		SimpleBean bean = clazz.newInstance();
		System.out.println(clazz.getName());
		bean.set("v1", "111");
		bean.set("v2", "222");
		System.out.println(bean.get("v1"));
		System.out.println(bean.get("v2"));
		System.out.println(bean.get("v3"));
	}

	//使用新类中生成的get-set方法
	private static void test1(Class<? extends SimpleBean> clazz) throws InstantiationException, IllegalAccessException {
		long l = System.currentTimeMillis();
		for (int i = 0; i < 100000000; i++) {
			SimpleBean bean = clazz.newInstance();
			bean.set("v1", "111");
			bean.set("v2", bean.get("v1"));
		}
		System.out.println("-> " + (System.currentTimeMillis() - l));
	}

	//使用反射读写字段
	private static void test2(Class<? extends SimpleBean> clazz)
			throws NoSuchFieldException, SecurityException, InstantiationException, IllegalAccessException {
		Field field1 = clazz.getDeclaredField("v1");
		Field field2 = clazz.getDeclaredField("v2");
		Field field3 = clazz.getDeclaredField("v3");
		field1.setAccessible(true);
		field2.setAccessible(true);
		field3.setAccessible(true);
		long l = System.currentTimeMillis();
		for (int i = 0; i < 100000000; i++) {
			SimpleBean bean = clazz.newInstance();
			field1.set(bean, "111");
			field2.set(bean, field1.get(bean));
		}
		System.out.println("-> " + (System.currentTimeMillis() - l));
	}

	//使用MAP类替代
	private static void test3() {
		long l = System.currentTimeMillis();
		for (int i = 0; i < 100000000; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("v1", "111");
			map.put("v2", map.get("v1"));
		}
		System.out.println("-> " + (System.currentTimeMillis() - l));
	}
}
