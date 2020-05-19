package yyl.example.demo.mybatis;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

public class MetaObjectExample {
	public static void main(String[] args) {
		PrivateBean bean = new PrivateBean();
		MetaObject meta = SystemMetaObject.forObject(bean);
		meta.setValue("value", "hello");
		System.out.println(meta.getValue("value"));
	}
}
