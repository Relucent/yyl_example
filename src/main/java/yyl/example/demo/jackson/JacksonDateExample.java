package yyl.example.demo.jackson;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * ZonedDateTime的序列化与反序列化
 */
public class JacksonDateExample {

    public static void main(String[] args) throws Throwable {

        ObjectMapper mapper = new ObjectMapper()//
                .disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)// 不转换为UTC零时区
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)// 不转换为时间戳
                .registerModule(new JavaTimeModule());// 可以用 findAndRegisterModules();

        ZonedDateTime sample = ZonedDateTime.now();
        String json = mapper.writeValueAsString(sample);
        System.out.println(json);

        ZonedDateTime actuals = mapper.readValue(json, ZonedDateTime.class);
        System.out.println(actuals);

    }
}
