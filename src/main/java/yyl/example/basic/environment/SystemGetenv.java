package yyl.example.basic.environment;

import java.util.Map;

/**
 * System.getenv() 方法是获取指定的环境变量的值，返回的变量大多于系统相关。
 */
public class SystemGetenv {
    public static void main(String[] args) {
        Map<String, String> env = System.getenv();
        for (Map.Entry<String, String> entry : env.entrySet()) {
            System.out.println(entry.getKey() + "=" + entry.getValue());
        }
    }
}
