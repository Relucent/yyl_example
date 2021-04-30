package yyl.example.basic.reflect;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * 通过反射修改注解信息<br>
 */
public class ModifyAnnotationTest {

    public static void main(String[] args) throws ReflectiveOperationException {
        Class<SampleBean> sampleType = SampleBean.class;
        SampleAnnotation sampleAnnotation = sampleType.getAnnotation(SampleAnnotation.class);
        System.out.println(sampleAnnotation);
        System.out.println(sampleAnnotation.getClass());
        modifyAnnotationProperty(sampleAnnotation, "value", "Modified");
        System.out.println(sampleAnnotation);
    }

    private static void modifyAnnotationProperty(Annotation annotation, String name, Object value) throws ReflectiveOperationException {
        InvocationHandler handler = Proxy.getInvocationHandler(annotation);
        Field memberValuesField = handler.getClass().getDeclaredField("memberValues");
        memberValuesField.setAccessible(true);
        @SuppressWarnings("unchecked")
        Map<String, Object> memberValues = (Map<String, Object>) memberValuesField.get(handler);
        memberValues.put("value", "Modified");
    }

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    static @interface SampleAnnotation {
        String value();
    }

    @SampleAnnotation("Original")
    static class SampleBean {

    }
}
