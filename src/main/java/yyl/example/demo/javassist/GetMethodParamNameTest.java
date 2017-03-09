package yyl.example.demo.javassist;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;

/**
 * JDK1.7及以下版本的API并不能获取到函数的参数名称，需要使用字节码工具ASM,javassist来实现这一功能.<br>
 * 在Java1.8之后，可以通过反射API java.lang.reflect.Executable.getParameters来获取到方法参数的元信息<br>
 * 但是要求在使用编译器时加上-parameters参数，它会在生成的.class文件中额外存储参数的元信息，这会增加class文件的大小<br>
 */
public class GetMethodParamNameTest {

	static class Test {

		void method1(String name1, Object value1) {
		}

		static void method2(String name2, Object value2) {
		}
	}

	public static void main(String[] args) throws NoSuchMethodException, SecurityException, NotFoundException {
		Method method1 = Test.class.getDeclaredMethod("method1", String.class, Object.class);
		Method method2 = Test.class.getDeclaredMethod("method2", String.class, Object.class);
		Method method3 = String.class.getDeclaredMethod("split", String.class);
		System.out.println(Arrays.toString(getMethodParamNames(method1)));
		System.out.println(Arrays.toString(getMethodParamNames(method2)));
		System.out.println(Arrays.toString(getMethodParamNames(method3)));
	}

	/**
	 * 获得参数名 (JDK 自带类 ，接口方法和抽象方法无法正确获取参数名)
	 */
	private static String[] getMethodParamNames(final Method method) throws NotFoundException {

		final String methodName = method.getName();
		final Class<?>[] methodParameterTypes = method.getParameterTypes();
		final int methodParameterCount = methodParameterTypes.length;
		final String className = method.getDeclaringClass().getName();
		final boolean isStatic = Modifier.isStatic(method.getModifiers());
		final String[] methodParametersNames = new String[methodParameterCount];

		ClassPool pool = ClassPool.getDefault();
		CtClass ctClass = pool.get(className);

		CtClass[] ctTypes = new CtClass[methodParameterTypes.length];
		for (int i = 0; i < methodParameterCount; i++) {
			ctTypes[i] = pool.get(methodParameterTypes[i].getName());
		}

		CtMethod ctMethod = ctClass.getDeclaredMethod(methodName, ctTypes);
		MethodInfo methodInfo = ctMethod.getMethodInfo();
		CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
		LocalVariableAttribute attribute = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);

		//如果是静态方法，第一个参数就是方法参数，非静态方法，则第一个参数是 this ,然后才是方法的参数
		if (attribute != null) {
			int variableCount = isStatic ? methodParameterCount : methodParameterCount + 1;

			for (int index = 0; index < variableCount; index++) {
				int methodParameterIndex = isStatic ? index : index - 1;
				if (0 <= methodParameterIndex && methodParameterIndex < methodParameterCount) {
					methodParametersNames[methodParameterIndex] = attribute.variableName(index);
				}
			}
		}
		return methodParametersNames;
	}

}
