package yyl.example.basic.jdk8.nashorn;

import javax.script.ScriptEngine;

import jdk.nashorn.api.scripting.ClassFilter;
import jdk.nashorn.api.scripting.NashornScriptEngineFactory;

/**
 * 类的过滤器
 */
public class MyClassFilterTest {

	class MyCF implements ClassFilter {
		@Override
		public boolean exposeToScripts(String s) {
			System.out.println("--- " + s);
			if (s.compareTo("java.io.File") == 0) {
				return false;
			}
			return true;
		}
	}

	public void testClassFilter() {
		NashornScriptEngineFactory factory = new NashornScriptEngineFactory();
		ScriptEngine engine = factory.getScriptEngine(new MyClassFilterTest.MyCF());
		try {
			engine.eval(""//
					+ "print(java.lang.System.getProperty(\"java.home\"));" //
					+ "print(\"Create file variable\");" //
					+ "var File = Java.type(\"java.io.File\");");
		} catch (Exception e) {
			System.out.println("Exception caught: " + e.toString());
		}

		try {
			engine.eval("new java.io.File(\"\");");
		} catch (Exception e) {
			System.out.println("Exception caught: " + e.toString());
		}
		try {
			engine.eval("java.lang.Class.forName(\"java.io.File\");");
		} catch (Exception e) {
			System.out.println("Exception caught: " + e.toString());
		}
	}

	public static void main(String[] args) {
		MyClassFilterTest myApp = new MyClassFilterTest();
		myApp.testClassFilter();
	}
}