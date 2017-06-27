package yyl.example.basic.script;

import javax.script.Bindings;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.SimpleBindings;

public class CompilableTest {
	public static void main(String[] args) {
		try {
			ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");

			String script = "print('hello')";
			Bindings bindings = new SimpleBindings();

			if (engine instanceof Compilable) {
				System.out.println("Compiling....");
				Compilable compEngine = (Compilable) engine;
				CompiledScript cs = compEngine.compile(script);
				cs.eval(bindings);
			} else {
				engine.eval(script, bindings);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
