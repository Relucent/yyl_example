package yyl.example.demo.yaml;

import java.io.IOException;
import java.io.InputStream;

import org.yaml.snakeyaml.Yaml;

/**
 * Yaml是一种“是一个可读性高并且容易被人类阅读，容易和脚本语言交互，用来表达资料序列的编程语言。”<br>
 * 类似于XML和properties配置文件，但是更加简洁。它实质上是一种通用的数据串行化格式。<br>
 */
public class SnakeyamlTest {

	public static void main(String[] args) throws IOException {
		Yaml yaml = new Yaml();

		try (InputStream input = SnakeyamlTest.class.getResourceAsStream("sample.yml")) {
			Object result = yaml.load(input);
			System.out.println(result.getClass());
			System.out.println(result);
		}
	}
}
