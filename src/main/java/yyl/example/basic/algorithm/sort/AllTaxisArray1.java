package yyl.example.basic.algorithm.sort;

import java.util.LinkedList;
import java.util.List;

/**
 * 全排序
 * @author yyl
 */
public class AllTaxisArray1 {

	public static void main(String[] s) {
		long l = System.currentTimeMillis();
		Object[] src = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };

		List<Object> srclist = new LinkedList<Object>();
		for (int i = 0; i < src.length; i++) {
			srclist.add(src[i]);
		}
		;
		recursionAllTaxis(null, 0, srclist);
		System.out.println(System.currentTimeMillis() - l);
	}

	static public void recursionAllTaxis(Object[] arrays, int indexLevel,
			List<Object> srclist) {
		if (arrays == null)
			arrays = new Object[srclist.size()];
		if (srclist.size() == 0) {
			// for(Object o:arrays){
			// System.out.print(o);
			// System.out.print(';');
			// }
			// System.out.println();
		}
		else {
			for (int i = 0; i < srclist.size(); i++) {
				Object obj = srclist.get(i);
				arrays[indexLevel] = obj;
				srclist.remove(obj);
				recursionAllTaxis(arrays, indexLevel + 1, srclist);
				srclist.add(i, obj);
			}
		}
	}
}
