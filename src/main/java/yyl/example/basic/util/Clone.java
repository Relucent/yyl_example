package yyl.example.basic.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Clone {

	/**
	 * 深层拷贝对象
	 * @param src 被拷贝的对象
	 * @return 拷贝后的副本对象
	 */
	public static Object clone(Serializable src) {
		try {
			ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(byteOut);
			out.writeObject(src);
			out.close();
			ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
			ObjectInputStream in = new ObjectInputStream(byteIn);
			Object ret = in.readObject();
			in.close();
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		ArrayList<String> list = new ArrayList<String>();
		list.add("ABCDEFG");
		ArrayList<String> newlist = (ArrayList<String>) clone(list);
		System.out.println("newlist==list > " + (newlist == list) + "\r" + "list.equals(newlist) > " + list.equals(newlist));
	}
}
