package yyl.example.basic.environment;

import java.util.Map;

public class SystemProperties1 {
	public static void main(String[] args) {
		for (Map.Entry<Object, Object> entry : System.getProperties().entrySet()) {
			System.out.println(entry.getKey() + "		" + entry.getValue());
		}
	}
}
