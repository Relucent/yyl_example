package yyl.example.demo.guava.reflect;

import java.util.List;
import java.util.Map;

import com.google.common.reflect.TypeToken;

/**
 * Java不能在运行时保留对象的泛型类型信息。例如可以将List<String>通过原始对象List强制转换成List<Object> <br>
 * 但是，反射允许你去检测方法和类的泛型类型。例如实现了一个返回List的方法，并且你用反射获得了这个方法的返回类型，会获得代表List<String>的ParameterizedType。 <br>
 * TypeToken类使用这种变通的方法以最小的语法开销去支持泛型类型的操作。<br>
 */
public class TypeTokenTest {

	@SuppressWarnings("serial")
	public static void main(String[] args) {
		//获取一个基本的、原始类的TypeToken 
		TypeToken<String> stringTok = TypeToken.of(String.class);
		System.out.println(stringTok);

		TypeToken<Integer> intTok = TypeToken.of(Integer.class);
		System.out.println(intTok);

		//获得一个含有泛型的类型的TypeToken
		TypeToken<List<String>> stringListTok = new TypeToken<List<String>>() {
		};
		System.out.println(stringListTok);
		
		//通配符类型
		TypeToken<Map<?, ?>> wildMapTok = new TypeToken<Map<?, ?>>() {};
		System.out.println(wildMapTok);
	}
}
