package yyl.example.demo.cglib;

import java.util.Map;

import net.sf.cglib.beans.BeanMap;

public class BeanMapTest {

    public static void main(String args[]) {
        SampleBean sample = new SampleBean();
        sample.setName("hello");
        sample.setValue("world");
        BeanMap map = BeanMap.create(new SampleBean());
        map.setBean(sample);
        for (Object o : map.entrySet()) {
            @SuppressWarnings({"unchecked", "rawtypes"})
            Map.Entry<String, Object> entry = (Map.Entry) o;
            System.out.println(entry.getKey() + "->" + entry.getValue());
        }
    }

    static class SampleBean {

        private String name;
        private String value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
