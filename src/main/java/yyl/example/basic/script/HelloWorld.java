package yyl.example.basic.script;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class HelloWorld {
	public static void main(String[] args) throws Exception {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("JavaScript");
		testHelloWorld(engine);
		testScriptVariables(engine);
		testInvokeScriptMethod(engine);
		testScriptInterface(engine);
		testUsingJDKClasses(engine);
	}

	public static void testHelloWorld(ScriptEngine engine) throws ScriptException {
		engine.eval("print ('Hello World')");
	}

	public static void testScriptVariables(ScriptEngine engine) throws ScriptException {
		String value = "Hello World!";
		engine.put("value", value);
		engine.eval("println(value)");
	}

	public static void testInvokeScriptMethod(ScriptEngine engine) throws Exception {
		String script = "function hello(name) { return 'Hello,' + name;}";
		engine.eval(script);
		Invocable inv = (Invocable) engine;
		String result = (String) inv.invokeFunction("hello", "World!!");
		System.out.println("res:" + result);
	}

	public static void testScriptInterface(ScriptEngine engine) throws ScriptException {
		String script = "var obj = new Object(); obj.run = function() { println('run method called'); }";
		engine.eval(script);
		Object obj = engine.get("obj");
		Invocable inv = (Invocable) engine;
		Runnable r = inv.getInterface(obj, Runnable.class);
		Thread th = new Thread(r);
		th.start();
	}

	public static void testUsingJDKClasses(ScriptEngine engine) throws Exception {
		// Packages是脚本语言里的一个全局变量,专用于访问JDK的package
		String js = "function doSwing(t){var f=new Packages.javax.swing.JFrame(t);f.setSize(400,300);f.setVisible(true);}";
		engine.eval(js);
		Invocable inv = (Invocable) engine;
		inv.invokeFunction("doSwing", "Scripting Swing");
	}
}