package yyl.example.demo.rhino;

import java.util.HashMap;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleBindings;

import yyl.example.basic.util.IoUtil;

/**
 * _Rhino 是由 Java语言编写的 JavaScript 开源引擎，原先由_Mozilla_开发，曾经被集成进入JDK 6.0。<br>
 * 因为 JDK8 使用 Nashorn替代了Rhino，如果使用Rhino引擎，需要引入第三方包(例如：cat.inspiracio:rhino-js-engine)来支持。
 */
public class RhinoJsEngineExample {

    public static void main(String[] args) throws ScriptException {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("rhino");
        Bindings bindings = new SimpleBindings();
        bindings.put("map", new HashMap<>());
        bindings.put("_", new PrimaryLibrary());
        String script = IoUtil.getResourceAsString(RhinoJsEngineExample.class, "sample.rhino.js");
        Object result = engine.eval(script, bindings);
        System.out.println(result);
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
