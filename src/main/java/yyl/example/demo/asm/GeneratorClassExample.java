package yyl.example.demo.asm;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

//通过ASM生成类的字节码
public class GeneratorClassExample {

	public static void main(String[] args) throws IOException {
		// 生成一个类只需要ClassWriter组件即可
		ClassWriter cw = new ClassWriter(0);
		// 通过visit方法确定类的头部信息
		cw.visit(Opcodes.V1_5, Opcodes.ACC_PUBLIC + Opcodes.ACC_ABSTRACT + Opcodes.ACC_INTERFACE,
				"yyl/example/demo/asm/base/IDemo", null, "java/lang/Object", new String[] {});
		// 定义类的属性
		cw.visitField(Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL + Opcodes.ACC_STATIC, "VALUE", "I", null, new Integer(-1))
				.visitEnd();
		// 定义类的方法
		cw.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_ABSTRACT, "compareTo", "(Ljava/lang/Object;)I", null, null)
				.visitEnd();
		cw.visitEnd(); // 使cw类已经完成
		// 将cw转换成字节数组写到文件里面去
		byte[] code = cw.toByteArray();

		Class<?> clazz = Util.defineClass(code);

		System.out.println(clazz.getName());
		for (Field field : clazz.getDeclaredFields()) {
			System.out.println(field);
		}
		for (Method method : clazz.getDeclaredMethods()) {
			System.out.println(method);
		}
	}
}