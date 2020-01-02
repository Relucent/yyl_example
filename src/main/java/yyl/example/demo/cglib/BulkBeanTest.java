package yyl.example.demo.cglib;

import net.sf.cglib.beans.BulkBean;

/**
 * BulkBean将整个Copy的动作拆分为getPropertyValues，setPropertyValues的两个方法，允许自定义处理的属性。
 */
public class BulkBeanTest {

    public static void main(String args[]) {
        String[] getter = {"getValue"};
        String[] setter = {"setValue"};
        Class<?>[] clazzs = {String.class};
        BulkBean bulkBean = BulkBean.create(SampleBean.class, getter, setter, clazzs);
        SampleBean sample = new SampleBean();
        sample.setValue("hello");
        Object[] propertyValues = bulkBean.getPropertyValues(sample);
        for (Object value : propertyValues) {
            System.out.println(value);
        }
    }

    static class SampleBean {

        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
