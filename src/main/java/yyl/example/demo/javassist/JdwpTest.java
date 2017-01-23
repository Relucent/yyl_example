package yyl.example.demo.javassist;

import java.io.IOException;

import com.sun.jdi.connect.IllegalConnectorArgumentsException;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.NotFoundException;
import javassist.util.HotSwapper;

/**
 * (Java Debug Wire Protocol Transport Interface) <br>
 * 运行时需要加参数:<br>
 * -agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=1234
 */
public class JdwpTest {

	public static void main(String[] args)
			throws IOException, IllegalConnectorArgumentsException, CannotCompileException, NotFoundException {

		ClassPool pool = ClassPool.getDefault();
		CtClass cc = pool.makeClass("yyl.test.TestClass");

		CtField cf = new CtField(pool.get("java.lang.String"), "name", cc);
		cc.addField(cf);

		HotSwapper hs = new HotSwapper(1234);
		hs.reload(cc.getName(), cc.toBytecode());
		cc.toClass();

	}
}