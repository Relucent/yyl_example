package yyl.example.basic.jdk8.nashorn;

import java.util.Set;

import jdk.nashorn.api.scripting.ClassFilter;

public class SandboxClassFilter implements ClassFilter {
	private final Set<String> allowed;

	@Override
	public boolean exposeToScripts(final String className) {
		return this.allowed.contains(className);
	}

	public SandboxClassFilter(final Set<String> allowed) {
		this.allowed = allowed;
	}
}