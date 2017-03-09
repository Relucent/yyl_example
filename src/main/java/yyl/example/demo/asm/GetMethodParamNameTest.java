package yyl.example.demo.asm;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodAdapter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

/**
 * JDK1.7及以下版本的API并不能获取到函数的参数名称，需要使用字节码工具ASM,javassist来实现这一功能.<br>
 * (JDK 自带类 ，接口方法和抽象方法无法正确获取参数名)<br>
 * 在Java1.8之后，可以通过反射API java.lang.reflect.Executable.getParameters来获取到方法参数的元信息<br>
 * 但是要求在使用编译器时加上-parameters参数，它会在生成的.class文件中额外存储参数的元信息，这会增加class文件的大小<br>
 */
public class GetMethodParamNameTest {

	static class Test {
		void method(String name, Object value) {
		}
	}

	public static void main(String[] args) throws SecurityException, NoSuchMethodException, IOException {
		Method method1 = Test.class.getDeclaredMethod("method", String.class, Object.class);
		printlnMethodParamNames(method1);
	}

	private static void printlnMethodParamNames(final Method method) throws IOException {
		System.out.println(method);
		System.out.println(Arrays.toString(getMethodParamNames(method)));
	}

	/** 使用字节码工具ASM来获取方法的参数名 */
	public static String[] getMethodParamNames(final Method method) throws IOException {

		final String methodName = method.getName();
		final Class<?>[] methodParameterTypes = method.getParameterTypes();
		final int methodParameterCount = methodParameterTypes.length;
		final String className = method.getDeclaringClass().getName();
		final boolean isStatic = Modifier.isStatic(method.getModifiers());
		final String[] methodParametersNames = new String[methodParameterCount];

		ClassReader cr = new ClassReader(className);
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		cr.accept(new ClassAdapter(cw) {
			public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {

				MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);

				final Type[] argTypes = Type.getArgumentTypes(desc);

				//参数类型不一致
				if (!methodName.equals(name) || !matchTypes(argTypes, methodParameterTypes)) {
					return mv;
				}
				return new MethodAdapter(mv) {
					public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {
						//如果是静态方法，第一个参数就是方法参数，非静态方法，则第一个参数是 this ,然后才是方法的参数
						int methodParameterIndex = isStatic ? index : index - 1;
						if (0 <= methodParameterIndex && methodParameterIndex < methodParameterCount) {
							methodParametersNames[methodParameterIndex] = name;
						}
						super.visitLocalVariable(name, desc, signature, start, end, index);
					}
				};
			}
		}, 0);
		return methodParametersNames;
	}

	/**
	 * 比较参数是否一致
	 * @param types
	 * @param parameterTypes
	 * @return
	 */
	private static boolean matchTypes(Type[] types, Class<?>[] parameterTypes) {
		if (types.length != parameterTypes.length) {
			return false;
		}
		for (int i = 0; i < types.length; i++) {
			if (!Type.getType(parameterTypes[i]).equals(types[i])) {
				return false;
			}
		}
		return true;
	}

}
