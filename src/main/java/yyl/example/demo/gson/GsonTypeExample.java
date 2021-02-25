package yyl.example.demo.gson;

import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 使用 TypeToken 支持泛型
 */
public class GsonTypeExample {

    public static void main(String[] args) throws IOException {

        String json = "{\"list\":[1,2,3,4,5]}";

        TypeToken<ConcurrentHashMap<String, List<BigDecimal>>> token = new TypeToken<ConcurrentHashMap<String, List<BigDecimal>>>() {
        };
        Gson gson = new Gson();
        println(gson.getAdapter(token).fromJson(json));
        System.out.println("\n");
        Type typeOf = token.getType();
        println(gson.fromJson(json, typeOf));
    }

    private static void println(ConcurrentHashMap<String, List<BigDecimal>> map) {
        System.out.println(map.getClass());
        System.out.println(map.get("list").getClass());
        System.out.println(map.get("list").get(0).getClass());
        System.out.println(map);
    }
}
