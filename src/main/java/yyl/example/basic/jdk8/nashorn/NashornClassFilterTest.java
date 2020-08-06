package yyl.example.basic.jdk8.nashorn;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.script.ScriptEngine;

import jdk.nashorn.api.scripting.ClassFilter;
import jdk.nashorn.api.scripting.NashornScriptEngineFactory;

/**
 * 类的过滤器
 */
@SuppressWarnings("restriction")
public class NashornClassFilterTest {
	public static void main(String[] args) {

		Set<String> allowed = new HashSet<>();
		allowed.add("java.lang.System");
		allowed.add("java.lang.Class");

		NashornScriptEngineFactory factory = new NashornScriptEngineFactory();
		ScriptEngine engine = factory.getScriptEngine(new SandboxClassFilter(allowed));
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

	private static class SandboxClassFilter implements ClassFilter {
		private final Set<String> allowed;

		public SandboxClassFilter(Collection<String> allowed) {
			(this.allowed = new HashSet<>()).addAll(allowed);
		}

		@Override
		public boolean exposeToScripts(String className) {
			System.out.println("className->{" + className + "}");
			return this.allowed.contains(className);
		}
	}
}