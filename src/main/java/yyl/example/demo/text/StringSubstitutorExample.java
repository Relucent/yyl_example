package yyl.example.demo.text;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.text.StringSubstitutor;

/**
 * Apache Commons Lang的StringSubstitutor 是一个高效的模板引擎，适用于大量占位符的高效替换。
 */
public class StringSubstitutorExample {
	public static void main(String[] args) {
		// 定义一个使用 <% 和 %> 作为占位符的模板字符串
		String template = "hello, <%name%>";

		// 创建键值对映射
		Map<String, String> valuesMap = new HashMap<>();
		valuesMap.put("name", "StringSubstitutor");

		// 创建 StringSubstitutor 实例，并指定自定义的前缀和后缀
		StringSubstitutor sub = new StringSubstitutor(valuesMap, "<%", "%>");

		// 执行替换
		String resolvedString = sub.replace(template);

		// 输出: hello, StringSubstitutor
		System.out.println(resolvedString);
	}
}
