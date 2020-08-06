package yyl.example.demo.rhino;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

/**
 * _Rhino 是由 Java语言编写的 JavaScript 开源引擎，原先由_Mozilla_开发，曾经被集成进入JDK 6.0。<br>
 */
public class RhinoExample {
	public static void main(String[] args) {
		Context ctx = Context.enter();
		try {
			Scriptable scope = ctx.initStandardObjects();
			ctx.setOptimizationLevel(-1);
			ctx.setLanguageVersion(Context.VERSION_1_8);
			ScriptableObject.putProperty(scope, "x", 1);
			ScriptableObject.putProperty(scope, "y", 2);
			System.out.println("x=" + scope.get("x", scope));
			System.out.println("y=" + scope.get("y", scope));
			String script = "x+y";
			Object result = ctx.evaluateString(scope, script, null, 0, null);
			System.out.println("x+y=" + result);
		} finally {
			Context.exit();
		}
	}
}
