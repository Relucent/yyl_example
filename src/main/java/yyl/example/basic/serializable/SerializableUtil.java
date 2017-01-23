package yyl.example.basic.serializable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializableUtil {

	public static byte[] writeObject(Object obj) {
		ObjectOutputStream out = null;
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		try {
			out = new ObjectOutputStream(bytes);
			out.writeObject(obj);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
			}
		}
		return bytes.toByteArray();
	}

	public static Object readObject(byte[] src) {
		ObjectInputStream in = null;
		try {
			in = new ObjectInputStream(new ByteArrayInputStream(src));
			return in.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
			}
		}
		return null;
	}
}
