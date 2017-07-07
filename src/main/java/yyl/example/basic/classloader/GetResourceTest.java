package yyl.example.basic.classloader;

import org.springframework.util.ClassUtils;

/**
 * ClassLoader.getResource(path) 查找具有给定名称的资源<br>
 * 读取资源的 URL 对象；如果找不到该资源，或者调用者没有足够的权限获取该资源，则返回 null<br>
 */
public class GetResourceTest {
	public static void main(String[] args) {
		ClassLoader classLoader = ClassUtils.getDefaultClassLoader();
		System.out.println(classLoader.getResource("yyl/example/basic/classloader"));
		System.out.println(classLoader.getResource("yyl/example/basic/classloader/"));
		System.out.println(classLoader.getResource("yyl/example/basic/classloader/GetResourceTest.class"));
		System.out.println(classLoader.getResource("yyl/example/basic/classloader/Undefined"));
	}
}
