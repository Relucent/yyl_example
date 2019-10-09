package yyl.example.basic.jdk5;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

/**
 * 内省(Introspector) 是操作 javaBean 的 API，用来访问某个属性的 getter/setter 方法。<br>
 * 对于一个标准的 javaBean 来说，它包括属性、get 方法和 set 方法，这是一个约定俗成的规范。<br>
 * 为此 sun 提供了 Introspector 工具包，来使开发者更好或者更灵活的操作 javaBean。<br>
 * 内省机制是通过反射来实现的，BeanInfo用来暴露一个bean的属性、方法和事件，以后我们就可以操纵该JavaBean的属性。<br>
 */
public class IntrospectorTest {

    public static void main(String[] args) throws IntrospectionException {

        BeanInfo beanInfo = Introspector.getBeanInfo(Sample.class);

        // PropertyDescriptor 类表示 JavaBean 类通过存储器导出一个属性
        PropertyDescriptor[] proDescrtptors = beanInfo.getPropertyDescriptors();
        if (proDescrtptors != null && proDescrtptors.length > 0) {
            for (PropertyDescriptor pd : proDescrtptors) {
                String propertyName = pd.getName();
                Method propertyReadMethod = pd.getReadMethod();
                Method propertyWriteMethod = pd.getWriteMethod();
                System.out.println("#Property       => " + propertyName);
                System.out.println(" ReadMethod     => " + propertyReadMethod);
                System.out.println(" WriteMethod    => " + propertyWriteMethod);
                System.out.println();
            }
        }
    }

    @SuppressWarnings("unused")
    private static class Sample {
        private String name;
        private boolean value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isValue() {
            return value;
        }

        public void setValue(boolean value) {
            this.value = value;
        }
    }
}
