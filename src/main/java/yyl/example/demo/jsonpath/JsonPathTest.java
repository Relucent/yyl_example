package yyl.example.demo.jsonpath;

import com.jayway.jsonpath.JsonPath;

import yyl.example.basic.util.IoUtil;

/**
 * JsonPath是一个读取JSON的工具类，使用路径表达式来选取JSON中的节点或节点集。<br>
 * 官网地址: https://github.com/jayway/JsonPath
 */
public class JsonPathTest {
    public static void main(String[] args) {

        String json = IoUtil.getResourceAsString(JsonPathTest.class, "example.json");

        readAndPrint(json, "$.store.book[0].title");
        readAndPrint(json, "$.store.book[*].title");
        readAndPrint(json, "$.store.book[?(@.category == '名著')]");
        readAndPrint(json, "$.store.book[?(@.price>100)]");
        readAndPrint(json, "$.store.book[?(@.discount)]");
        readAndPrint(json, "$.store..title");

        JsonPath path = JsonPath.compile("$.store.computer[*]");
        readAndPrint(json, path);
    }

    private static void readAndPrint(String json, String path) {
        Object value = JsonPath.read(json, path);
        System.out.println(path);
        System.out.println(value.getClass());
        System.out.println(value);
        System.out.println();
    }

    private static void readAndPrint(String json, JsonPath path) {
        Object value = path.read(json);
        System.out.println(path);
        System.out.println(value.getClass());
        System.out.println(value);
        System.out.println();
    }
}

// |语法 |描述 |
// |:----- |:----- |
// |$ |根节点 |
// |@ |当前节点正在由筛选器谓词处理 |
// |* |通配符，匹配任何属性名称 |
// |.. |选择所有符合条件的节点 |
// |.or[] |子节点 |
// |[] |迭代器标示，如数组下标 |
// |[,] |支持迭代器中做多选 |
// |[start:end:step] |数组切片运算符 |
// |?() |支持过滤操作 |
// |() |支持表达式计算 |
