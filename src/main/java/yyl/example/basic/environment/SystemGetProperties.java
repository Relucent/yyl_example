package yyl.example.basic.environment;

import java.util.Map;
import java.util.Properties;

/**
 * System.getenv() 方法是获取指定的环境变量的值，返回的变量大多与java程序有关。
 */
public class SystemGetProperties {
    public static void main(String[] args) {
        Properties properties = System.getProperties();
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            System.out.println(entry.getKey() + "=" + entry.getValue());
        }
    }
}
