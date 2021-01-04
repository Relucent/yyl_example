package yyl.example.basic.reflect;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.io.Serializable;

import yyl.example.basic.util.SerializationUtil;

public class ObjectInputStreamTest {

    public static void main(String[] args) throws ClassNotFoundException, IOException {
        SampleBean sample = new SampleBean();
        sample.setId(1L);
        sample.setName("hello");
        sample.setValue("welcome");

        SampleBean clone = (SampleBean) resolve(sample);
        System.out.println(clone);
    }

    public static Object resolve(Object input) throws ClassNotFoundException, IOException {
        try (ObjectInputStream objIn = new ObjectInputStream(new ByteArrayInputStream(SerializationUtil.serialize(input))) {
            @Override
            protected Class<?> resolveClass(ObjectStreamClass objectStreamClass) throws IOException, ClassNotFoundException {
                Class<?> clazz = super.resolveClass(objectStreamClass);
                System.out.println(clazz);
                return clazz;
            }
        }) {
            return objIn.readObject();
        }
    }

    @SuppressWarnings("serial")
    public static class SampleBean implements Serializable {

        private Long id;
        private transient String name;
        private transient String value;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

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

        private void writeObject(java.io.ObjectOutputStream s) throws java.io.IOException {
            s.defaultWriteObject();
            s.writeObject(name);
        }

        private void readObject(java.io.ObjectInputStream s) throws java.io.IOException, ClassNotFoundException {
            s.defaultReadObject();
            name = (String) s.readObject();
        }

        @Override
        public String toString() {
            return "Sample [id=" + getId() + ", name=" + getName() + ", value=" + getValue() + "]";
        }
    }
}
