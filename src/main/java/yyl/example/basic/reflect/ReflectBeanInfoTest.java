package yyl.example.basic.reflect;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

public class ReflectBeanInfoTest {
	public static void main(String[] args) {
		Bean bean = new Bean();
		//PropertyDescriptor[] pds = PropertyUtils.getPropertyDescriptors(bean.getClass(),Introspector.USE_ALL_BEANINFO);
		PropertyDescriptor[] descriptors = null;
		try {
			descriptors = Introspector.getBeanInfo(bean.getClass(), Introspector.IGNORE_ALL_BEANINFO)
					.getPropertyDescriptors();
		} catch (IntrospectionException e) {
			descriptors = new PropertyDescriptor[0];
		}

		for (PropertyDescriptor pd : descriptors) {
			System.out.println(pd.getDisplayName());
		}
	}

	public static String decapitalize(String name) {
		if (name == null || name.length() == 0) {
			return name;
		}
		if (name.length() > 1 && Character.isUpperCase(name.charAt(1)) && Character.isUpperCase(name.charAt(0))) {
			return name;
		}
		char chars[] = name.toCharArray();
		chars[0] = Character.toLowerCase(chars[0]);
		return new String(chars);
	}
}

class BeanP {
	String b;

	public String getB() {
		return b;
	}

	public void setB(String b) {
		this.b = b;
	}
}

class Bean extends BeanP {
	String a = "2";

	public void setC(String c) {

	}

	public String getUPPER() {
		return "UPPER";
	}

	public String getC() {
		return "";
	}
}
