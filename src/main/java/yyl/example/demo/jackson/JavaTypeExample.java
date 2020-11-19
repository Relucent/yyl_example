package yyl.example.demo.jackson;

import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 使用 JavaType 支持泛型
 */
public class JavaTypeExample {

    public static void main(String[] args) throws IOException {

        String json = "{\"list\":[1,2,3,4,5]}";

        ObjectMapper om = new ObjectMapper();

        Type type = (new TypeReference<ConcurrentHashMap<String, List<BigDecimal>>>() {
        }).getType();

        JavaType valueType = om.getTypeFactory().constructType(type);

        ConcurrentHashMap<String, List<BigDecimal>> map = om.readValue(json, valueType);
        System.out.println(map.getClass());
        System.out.println(map.get("list").getClass());
        System.out.println(map.get("list").get(0).getClass());
        System.out.println(map);
    }
}
