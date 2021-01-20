package yyl.example.demo.jackson;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 使用_Jackson进行序列化与反序列化
 */
public class JacksonTestExample {

    public static void main(String[] args) throws Throwable {

        Sample sample = new Sample();

        ObjectMapper om1 = new ObjectMapper();
        System.out.println(om1.writeValueAsString(sample));

        // 设置使用字段方式序列化，默认是用 GETTER/SETTER 方法
        ObjectMapper om2 = new ObjectMapper()//
                .setVisibility(PropertyAccessor.SETTER, Visibility.NONE)//
                .setVisibility(PropertyAccessor.GETTER, Visibility.NONE)//
                .setVisibility(PropertyAccessor.FIELD, Visibility.ANY);

        System.out.println(om2.writeValueAsString(sample));

        // 设置使用字段方式序列化， GETTER方法优先于字段
        ObjectMapper om3 = new ObjectMapper()//
                .setVisibility(PropertyAccessor.SETTER, Visibility.ANY)//
                .setVisibility(PropertyAccessor.GETTER, Visibility.ANY)//
                .setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
        System.out.println(om3.writeValueAsString(sample));
    }

    @SuppressWarnings({ "serial", "unused" })
    private static class Sample implements Serializable {

        private String value = "jackson";

        public String getName() {
            return value + "$name";
        }

        public void setName(String value) {
            this.value = value;
        }

        public String getValue() {
            return value + "$value";
        }

        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Sample [value=" + value + "]";
        }
    }
}
