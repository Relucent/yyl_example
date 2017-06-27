package yyl.example.basic.script;

import java.util.List;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleBindings;

public class BindingsScriptTest {

	public static void main(String[] args) throws ScriptException {

		String script = ""//
				+ " Util.each([ 1, 2, 3, 4, 5 ], function(value, index, array) {"//
				+ "  Util.println(value);"//
				+ " }); ";//

		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("JavaScript");
		Bindings bindings = new SimpleBindings();
		bindings.put("Util", new Util());
		engine.setBindings(bindings, ScriptContext.ENGINE_SCOPE);
		engine.eval(script);
	}

	//定义一个工具类
	public static class Util {

		public void each(List<Object> list, ForEachConsumer consumer) {
			int i = 0;
			for (Object value : list) {
				consumer.accept(value, i++, list);
			}
		}

		public void println(Object x) {
			System.out.println(x);
		}
	}

	//定义forEach回调方法
	public static interface ForEachConsumer {
		void accept(Object value, Integer i, List<Object> list);
	}
}
