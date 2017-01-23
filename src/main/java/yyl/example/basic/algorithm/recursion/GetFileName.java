package yyl.example.basic.algorithm.recursion;

import java.io.File;
import java.util.ArrayList;

/**
 * 递归获得文件列表
 */
public class GetFileName {

	static ArrayList<String> src = new ArrayList<String>();

	public static void main(String[] arg) {
		long time0 = System.currentTimeMillis();

		File f = new File("C:/");

		pass(f);

		//recursion(f);

		long time1 = System.currentTimeMillis();

		System.out.println(src.size());
		System.out.println(time1 - time0);
	}

	public static void recursion(File f) {
		// System.out.println(f.getAbsolutePath());
		src.add(f.getAbsolutePath());
		if (f.isDirectory()) {
			File[] cs = f.listFiles();
			if (cs != null) {
				for (int i = 0; i < cs.length; i++)
					recursion(cs[i]);
			}
		}
	}

	public static void pass(File f) {
		ArrayList<File> list = new ArrayList<File>();
		ArrayList<File> temp = null;
		list.add(f);
		src.add(f.getAbsolutePath());
		while (list.size() > 0) {
			temp = new ArrayList<File>();
			for (int i = 0; i < list.size(); i++) {
				f = (File) list.get(i);
				File[] cs = f.listFiles();
				if (cs != null) {
					for (int j = 0; j < cs.length; j++) {
						File fc = cs[j];
						if (fc.isDirectory()) {
							temp.add(fc);
						}
						src.add(f.getAbsolutePath());
					}
				}
			}
			list = temp;
		}
	}
}
