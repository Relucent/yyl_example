package yyl.example.demo.fst;

import java.io.Serializable;
import java.util.Objects;

import org.nustaq.serialization.FSTConfiguration;

/**
 * FST序列化全称是 Fast Serialization，是一个快速对象序列化开发包，兼容原生 JDK 环境，并且序列化速度大概是JDK的4-10倍，大小是JDK大小的1/3左右<br>
 * @see <a href="https://github.com/RuedigerMoeller/fast-serialization">fast-serialization</a>
 */
public class FstExample {

    public static void main(String[] args) {

        FSTConfiguration fst = FSTConfiguration.getDefaultConfiguration();

        SampleBean sample = new SampleBean();
        sample.setId(1L);
        sample.setName("hello");

        System.out.println("serialization: " + sample);

        byte[] bytes = fst.asByteArray(sample);

        SampleBean result = (SampleBean) fst.asObject(bytes);
        System.out.println("deserialization: " + result);

        System.out.println("equals: " + sample.equals(result));
    }

    @SuppressWarnings("serial")
    static class SampleBean implements Serializable {

        private Long id;
        private String name;

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

        @Override
        public String toString() {
            return "SampleBean [id=" + id + ", name=" + name + "]";
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof SampleBean)) {
                return false;
            }
            SampleBean other = (SampleBean) obj;
            return Objects.equals(id, other.id) && Objects.equals(name, other.name);
        }
    }
}
