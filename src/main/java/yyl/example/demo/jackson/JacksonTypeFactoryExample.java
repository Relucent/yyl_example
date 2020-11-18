package yyl.example.demo.jackson;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.redisson.codec.JsonJacksonCodec.ThrowableMixIn;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerator.Feature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.databind.type.TypeFactory;

/**
 * 使用_Jackson进行序列化与反序列化
 */
public class JacksonTypeFactoryExample {

    public static void main(String[] args) throws Throwable {
        ObjectMapper om = createObjectMapper();
        Map<String, Object> sample = new LinkedHashMap<>();
        sample.put("string", "hello world");
        sample.put("integer", 12345);
        sample.put("date", new Date());
        sample.put("list", new LinkedList<>());
        sample.put("map", new ConcurrentHashMap<>());
        sample.put("bigint", BigInteger.ZERO);
        sample.put("custom", new CustomBean());
        String json = om.writeValueAsString(sample);
        System.out.println(json);
        Object object = om.readValue(json, Object.class);
        @SuppressWarnings("unchecked")
        Map<String, Object> result = (Map<String, Object>) object;
        for (Map.Entry<String, Object> entry : result.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            System.out.println(key + "=" + value + "#" + value.getClass());
        }
    }

    private static ObjectMapper createObjectMapper() {
        ObjectMapper om = new ObjectMapper();

        TypeFactory tf = TypeFactory.defaultInstance();
        VisibilityChecker<?> visibility = om.getSerializationConfig().getDefaultVisibilityChecker();
        visibility.withFieldVisibility(JsonAutoDetect.Visibility.ANY);
        visibility.withGetterVisibility(JsonAutoDetect.Visibility.NONE);
        visibility.withSetterVisibility(JsonAutoDetect.Visibility.NONE);
        visibility.withCreatorVisibility(JsonAutoDetect.Visibility.NONE);

        om.activateDefaultTyping(om.getPolymorphicTypeValidator(), DefaultTyping.NON_FINAL);
        om.addMixIn(Throwable.class, ThrowableMixIn.class);
        om.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        om.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        om.enable(Feature.WRITE_BIGDECIMAL_AS_PLAIN);
        om.enable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY);
        om.setSerializationInclusion(Include.ALWAYS);
        om.setTypeFactory(tf);
        om.setVisibility(visibility);
        return om;
    }

    @SuppressWarnings("serial")
    private static class CustomBean implements Serializable {

        public String name = "hello";

        @Override
        public String toString() {
            return "CustomBean [name=" + name + "]";
        }
    }
}
