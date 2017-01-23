package yyl.example.basic.script;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * Java8_Nashorn JavaScript 引擎<br>
 * 在Java调用JavaScript,以及 JavaScript中调用Java
 */
public class Java8JsTester {
	public static void main(String args[]) {
		ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
		ScriptEngine nashorn = scriptEngineManager.getEngineByName("nashorn");
		try {

			String script = ""//
					+ " var T = Java.type('" + Java8JsTester.class.getName() + "'); \n"//
					+ " T.test1(); \n"//
					+ " var map = new java.util.HashMap(); \n"//
					+ " map.put('k1','V1'); \n"//
					+ " map.put('k2','V2'); \n"//
					+ " map.put('k3','V3'); \n"//
					+ " print(map); \n"//
					+ " map.remove('k3'); \n"//
					+ " print(map); \n"//
					+ " map;";

			Object result = nashorn.eval(script);

			System.out.println("result\n type->" + result.getClass());
			System.out.println(result);

		} catch (ScriptException e) {
			e.printStackTrace();
		}
	}

	public static void test1() {
		System.out.println("InvokeMethod:Test1");
	}
}
