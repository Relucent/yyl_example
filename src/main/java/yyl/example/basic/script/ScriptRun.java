package yyl.example.basic.script;

import java.io.IOException;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import yyl.example.basic.util.Streams;

public class RunScript {

	public static void main(String[] args) throws Throwable {
		String script = getScript("demo.js");
		ScriptEngine engine = getEngine();
		engine.put("_", getPrimaryLibrary());
		engine.put("$map", new java.util.HashMap<>());
		engine.eval(script);
	}

	private static ScriptEngine getEngine() {
		ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
		ScriptEngine engine = scriptEngineManager.getEngineByName("nashorn");
		return engine;
	}

	private static String getScript(String path) throws IOException {
		return Streams.getResourceString(RunScript.class, path);
	}

	private static Object getPrimaryLibrary() {
		return new PrimaryLibrary();
	}

	public static class PrimaryLibrary {
		public void log(Object o) {
			if (o == null) {
				System.out.println("null");
			} else {
				System.out.println(o.getClass() + " " + o);
			}
		}
	}

}
