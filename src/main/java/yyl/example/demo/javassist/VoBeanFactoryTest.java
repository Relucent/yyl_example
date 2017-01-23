package yyl.example.demo.javassist;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtNewConstructor;
import javassist.CtNewMethod;

/**
 * VoBean 工厂类 <br>
 * 用 javassist 动态创建类的演示<br>
 */
public class VoBeanFactoryTest {

	private static AtomicInteger COUNT = new AtomicInteger();
	private static ConcurrentMap<String, Constructor<? extends VO>> cache = new ConcurrentHashMap<String, Constructor<? extends VO>>();
	private static ConcurrentMap<String, Boolean> error = new ConcurrentHashMap<String, Boolean>();
	private static final Object lock = new byte[0];

	public static VO create(String... fields) {

		Arrays.sort(fields = fields.clone());

		String key = Arrays.deepToString(fields);

		if (error.get(key) != null) {
			return null;
		}

		Constructor<? extends VO> constructor = cache.get(key);
		if (constructor == null) {
			synchronized (lock) {
				try {
					constructor = createConstructor(fields);
				} catch (Exception e) {
					System.err.println(e);
				}

				if (constructor == null) {
					error.putIfAbsent(key, Boolean.TRUE);
				} else {
					cache.put(key, constructor);
				}
			}

		}
		if (constructor != null) {
			try {
				return constructor.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 创建构造函数
	 * @param fields 类的字段
	 * @return 新创建类的构造函数
	 */
	@SuppressWarnings("unchecked")
	private static Constructor<? extends VO> createConstructor(String... fields) throws Exception {
		ClassPool pool = ClassPool.getDefault();
		String interfaceName = VO.class.getName();
		final int count = COUNT.incrementAndGet();
		CtClass cc = pool.makeClass(interfaceName + "$" + count);
		cc.setInterfaces(new CtClass[] { pool.get(interfaceName) });
		CtClass stringCtClass = pool.get("java.lang.String");
		CtClass objectCtClass = pool.get("java.lang.Object");
		StringBuilder smb = new StringBuilder("if(null == $1){ throw new NullPointerException(\" key can not be NULL \"); }");
		StringBuilder gmb = new StringBuilder("if(null == $1){ throw new NullPointerException(\" key can not be NULL \"); }");
		for (String field : fields) {
			cc.addField(new CtField(objectCtClass, field, cc));
			smb.append("else if(\"").append(field).append("\".equals($1)){ $0.").append(field).append("=$2; }");
			gmb.append("else if(\"").append(field).append("\".equals($1)){ return $0.").append(field).append("; }");
		}
		smb.append("else { throw new IllegalArgumentException(\"Object does not exist in the \" + $1 + \" field\"); }");
		gmb.append("else { throw new IllegalArgumentException(\"Object does not exist in the \" + $1 + \" field\"); }");
		cc.addMethod(CtNewMethod.make(CtClass.voidType, "set", new CtClass[] { stringCtClass, objectCtClass }, null, smb.toString(), cc));
		cc.addMethod(CtNewMethod.make(objectCtClass, "get", new CtClass[] { stringCtClass }, null, gmb.toString(), cc));
		cc.addConstructor(CtNewConstructor.make(new CtClass[] {}, null, "{}", cc));
		cc.detach();

		final class CustomClassLoader extends ClassLoader {
			public Class<?> defineClass(byte[] code) {
				return defineClass(null, code, 0, code.length);
			}

			@Override
			protected void finalize() throws Throwable {
				super.finalize();
				System.out.println("GC->CL_" + count);
			}
		}

		Constructor<? extends VO> constructor = (Constructor<? extends VO>) new CustomClassLoader().defineClass(cc.toBytecode()).getConstructor();
		constructor.setAccessible(true);
		return constructor;
	}

	/** 清理缓存 */
	public static void clear() {
		System.out.println("destroy cache << " + cache.size());
		cache.clear();
		error.clear();
	}

	/** 查询构造函数缓存大小 */
	public static int size() {
		return cache.size();
	}

	/** 自定义类的接口 */
	public static interface VO {

		Object get(String name);

		void set(String name, Object value);
	}

	/** 测试 */
	public static void main(String[] args) throws Exception {
		for (int i = 0; i < 100; i++) {
			create("v1", "v2");

			create("v2", "v1");

			create("v1", "v2", "v2");// error

			create("v1", "v2", "v3", "v4", "v5");
		}

		// 因为缓存了构造函数，所以其实只创建了2个CLASS
		System.out.println("cl.size() -> " + size());

		clear();
		System.gc();
		Thread.sleep(1000);
	}
}
