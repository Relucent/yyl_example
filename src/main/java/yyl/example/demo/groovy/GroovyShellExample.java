package yyl.example.demo.groovy;

import groovy.lang.Binding;
import groovy.lang.Closure;
import groovy.lang.GroovyShell;

public class GroovyShellExample {

    public static void main(String[] args) throws Exception {
        Binding binding = new Binding();
        binding.setVariable("language", "Groovy");
        binding.setVariable("x", 1);
        binding.setVariable("y", 2);
        GroovyShell shell = new GroovyShell(binding);
        Object result = shell.evaluate("closure = {p->println \"${p}\"};println \"Welcome to ${language}\";x+y;");
        Object x = binding.getVariable("x");
        Object y = binding.getVariable("y");
        System.out.println(x + "+" + y + "=" + result);
        @SuppressWarnings({ "unchecked", "rawtypes" })
        Closure<String> closure = (Closure) binding.getVariable("closure");
        closure.call("hello");
    }
}
