package yyl.example.demo.asm;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * 使用 ASM 修改字节码，在类方法前增加操作<br>
 * ASM 版本 5.x <br>
 */
public class AsmProxyTest {

	public static void main(String[] args) throws IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			SecurityException, InstantiationException, NoSuchMethodException {
		ClassReader cr = new ClassReader(TargetClass.class.getName());
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		ClassVisitor cv = new CustomClassVisitor(Opcodes.ASM5, cw);
		cr.accept(cv, 0);

		byte[] code = cw.toByteArray();

		Class<?> appClass = Util.defineClass(code);

		Object object = appClass.newInstance();

		System.out.println("object.getClass()  -> " + object.getClass());
		System.out.println("object instanceof TargetClass  -> " + (object instanceof TargetClass));

		Method method = appClass.getMethod("say");
		method.invoke(object);
	}

	public static class TargetClass {
		public void say() {
			System.out.println("Hello World!");
		}
	}

	public static class MyInject {
		public static void invoke() {
			System.out.println("MyInject -> invoke");
		}
	}

	// ASM 3.x 中的 ClassAdapter 在 ASM4.x 中已经被  ClassVisitor 替代
	private static class CustomClassVisitor extends ClassVisitor {

		public CustomClassVisitor(int api, ClassWriter cw) {
			super(api, cw);
		}

		@Override
		public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature, final String[] exceptions) {
			MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
			if (name.equals("say")) {
				// # System.out.println(System.currentTimeMillis());
				mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
				mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
				mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(J)V", false);
				// # MyInject.invoke();
				mv.visitMethodInsn(Opcodes.INVOKESTATIC, "yyl/example/demo/asm/AsmProxyTest$MyInject", "invoke", "()V", false);
			}
			return mv;
		}
	}
}
