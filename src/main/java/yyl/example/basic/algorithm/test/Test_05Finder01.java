package yyl.example.basic.algorithm.test;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

//递归查找
//当两个路径同时满足规则时，返回先被查找到的路径(排序靠前的路径，但不一定是浅路径)
public class Test_05Finder01 {

	public static String findPath(Map<?, ?> o, String key) {
		LinkedList<Object> paths = new LinkedList<Object>();
		if (findPath(o, key, paths)) {
			return join(paths);
		} else {
			return null;
		}
	}

	private static boolean findPath(Object o, String key, LinkedList<Object> paths) {
		if (o instanceof Map) {
			Map<?, ?> map = (Map<?, ?>) o;
			for (Map.Entry<?, ?> entry : map.entrySet()) {
				Object k = entry.getKey();
				Object v = entry.getValue();
				paths.add(k);
				if (k.equals(key) || findPath(v, key, paths)) {
					return true;
				}
				paths.removeLast();
			}
		}
		return false;
	}

	private static String join(List<Object> list) {
		if (list == null) {
			return null;
		}
		StringBuilder buffer = new StringBuilder();
		for (int i = 0, j = list.size(); i < j; i++) {
			if (i != 0) {
				buffer.append(".");
			}
			buffer.append(list.get(i));
		}
		return buffer.toString();
	}
}
