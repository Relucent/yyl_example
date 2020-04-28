package yyl.example.demo.mybatis;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.builder.xml.XMLStatementBuilder;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.parsing.XPathParser;
import org.apache.ibatis.session.Configuration;

/**
 * 使用_Mybatis拼装SQL
 */
public class MybatisBoundSqlExample {

	public static void main(String[] args) throws IOException {

		// 解决一个不同包引起的冲突
		System.setProperty("javax.xml.parsers.DocumentBuilderFactory", "com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl");

		Configuration configuration = new Configuration();
		MapperBuilderAssistant builderAssistant = new MapperBuilderAssistant(configuration, null);
		// 定义 NameSpace
		builderAssistant.setCurrentNamespace("sample");

		try (InputStream input = MybatisBoundSqlExample.class.getResourceAsStream("sample-mapper.xml")) {
			// XML XPath解析器
			XPathParser parser = new XPathParser(input);
			// 获得select节点
			for (XNode node : parser.evalNodes("/mapper/select")) {
				// 解析节点
				XMLStatementBuilder statementParser = new XMLStatementBuilder(configuration, builderAssistant, node, null);
				// 这个操作会创建 MappedStatement ，并保存到configuration中
				statementParser.parseStatementNode();
			}
		}

		// 获得 getById
		MappedStatement ms = configuration.getMappedStatement("sample.selectList");

		// 定义参数
		Map<String, String> parameters = new HashMap<>();
		parameters.put("id", "1");

		// 根据参数获得 BoundSql
		BoundSql boundSql = ms.getBoundSql(parameters);

		// 打印最终SQL 和 参数
		System.out.println("[SQL]");
		System.out.println(boundSql.getSql());
		System.out.println("[PARAMETER]");
		for (ParameterMapping pm : boundSql.getParameterMappings()) {
			System.out.println(pm.getProperty() + "=>" + parameters.get(pm.getProperty()));
		}
	}
}
