package yyl.example.basic.algorithm.test;

import java.util.LinkedList;
import java.util.Map;

//递推(非递归)查找
//首先查找第一层，然后再查第二层，以此类推，将路径信息压到栈中
//当两个路径同时满足规则时，返回层级浅的路径
public class Test_05Finder02 {
	public static String findPath(Object o, String key) {
		LinkedList<Object> valueStack = new LinkedList<Object>();
		LinkedList<String> pathsStack = new LinkedList<String>();
		valueStack.add(o);
		pathsStack.add("");
		while (!valueStack.isEmpty()) {
			Object elem = valueStack.removeLast();
			String path = pathsStack.removeLast();
			if (elem instanceof Map) {
				Map<?, ?> map = (Map<?, ?>) elem;
				for (Map.Entry<?, ?> entry : map.entrySet()) {
					Object k = entry.getKey();
					Object v = entry.getValue();
					String r = path + "." + k;
					valueStack.add(v);
					pathsStack.add(r);
					if (k.equals(key)) {
						return r.substring(1);
					}
				}
			}
		}
		return null;
	}
}
