package yyl.example.basic.algorithm.test;

import java.util.HashMap;
import java.util.Map;

/**
 * 从MAP中查找元素
 */
public class Test_05 {
	@SuppressWarnings("serial")
	public static void main(String[] args) {

		Map<String, Object> o = new HashMap<String, Object>() {
			{
				put("A", new HashMap<String, Object>());
				put("B", new HashMap<String, Object>() {
					{
						put("C", "C");
					}
				});
				put("C", new HashMap<String, Object>() {
					{
						put("C1", new HashMap<String, Object>() {
							{
								put("C11", new HashMap<String, Object>() {
									{
										put("C111", Boolean.FALSE);
										put("C112", new HashMap<String, Object>());
									}
								});
							}
						});
					}
				});
			}
		};
		//o = {
		//	"A" : {},
		//	"B" : {
		//		"C" : "C"
		//	},
		//	"C" : {
		//		"C1" : {
		//			"C11" : {
		//				"C111" : false,
		//				"C112" : {}
		//			}
		//		}
		//	}
		//}

		for (String value : new String[] { "C", "C1", "C112" }) {
			System.out.println("find:" + value);
			System.out.println("finder01-> " + Test_05Finder01.findPath(o, value));
			System.out.println("finder02-> " + Test_05Finder02.findPath(o, value));
			System.out.println("finder03-> " + Test_05Finder03.findPath(o, value));
			System.out.println();
		}

	}

}