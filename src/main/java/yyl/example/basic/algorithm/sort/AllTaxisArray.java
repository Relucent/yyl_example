package yyl.example.basic.algorithm.sort;

/**
 * 全排序
 * @author yyl
 */
public class AllTaxisArray {

	public static void main(String[] s) {
		long l = System.currentTimeMillis();
		Object[] src = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		Object[] arrays = new Object[src.length];
		System.arraycopy(src, 0, arrays, 0, src.length);
		recursionAllTaxis(arrays, 0);
		System.out.println(System.currentTimeMillis() - l);
	}

	static public void recursionAllTaxis(Object[] arrays, int indexLevel) {
		if (indexLevel == arrays.length - 1) {
			// for(Object o:arrays){
			// System.out.print(o);
			// System.out.print(';');
			// }
			// System.out.println();
		}
		else {
			for (int i = indexLevel; i < arrays.length; i++) {
				swap(arrays, indexLevel, i);
				recursionAllTaxis(arrays, indexLevel + 1);
				swap(arrays, indexLevel, i);
			}
		}
	}

	final static public void swap(Object[] arrays, int index0, int index1) {
		Object obj = arrays[index0];
		arrays[index0] = arrays[index1];
		arrays[index1] = obj;
	}
}
