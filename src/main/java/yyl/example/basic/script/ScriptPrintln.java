package yyl.example.basic.script;

import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;

public class ScriptPrintln {
	public static void main(String[] args) {
		ScriptEngineManager factory = new ScriptEngineManager();
		for (ScriptEngineFactory available : factory.getEngineFactories()) {
			System.out.println(available.getEngineName());
		}
	}
}
