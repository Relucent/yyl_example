package yyl.example.basic.reflect.generic;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 泛型工具类。
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class GenericsUtil {

	/**
	 * 通过反射，获得定义Class时声明的父类的范型参数的类型：如public BookManager extends GenricManager<Book>。
	 * @param clazz The class to introspect.
	 * @return the first generic declaration, or <code>Object.class</code> if
	 *         cannot be determined.
	 */
	public static <T> Class<T> getSuperClassGenricType(Class clazz) {
		return (Class<T>) getSuperClassGenricType(clazz, 0);
	}

	/**
	 * 通过反射，获得定义Class时声明的父类的范型参数的类型：如public BookManager extends GenricManager<Book>。
	 * 
	 * @param clazz clazz The class to introspect．
	 * @param index the Index of the generic declaration, start from 0.
	 * @return the index generic declaration, or <code>Object.class</code> if
	 *         cannot be determined.
	 */
	public static Class<?> getSuperClassGenricType(Class<?> clazz, int index) {
		Type genType = clazz.getGenericSuperclass();

		if (!(genType instanceof ParameterizedType)) {
			return Object.class;
		}

		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

		if (index >= params.length || index < 0) {
			return Object.class;
		}
		if (!(params[index] instanceof Class)) {
			return Object.class;
		}

		return (Class<?>) params[index];
	}

	/**
	 * Locates the first generic declaration on a class.
	 * 
	 * @param clazz The class to introspect.
	 * @return the first generic declaration, or <code>null</code> if cannot
	 *         be determined.
	 */
	public static Class<?> getGenericClass(Class<?> clazz) {
		return getGenericClass(clazz, 0);
	}

	/**
	 * Locates generic declaration by index on a class.
	 * 
	 * @param clazz clazz The class to introspect.
	 * @param index the Index of the generic ddeclaration, start from 0.
	 * @return the index generic declaration, or <code>null</code> if cannot
	 *         be determined.
	 */
	public static Class<?> getGenericClass(Class<?> clazz, int index) {
		Type genType = clazz.getGenericSuperclass();

		if (genType instanceof ParameterizedType) {
			Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

			if ((params != null) && (params.length >= (index - 1))) {
				return (Class<?>) params[index];
			}
		}

		return null;
	}
}