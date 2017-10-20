package yyl.example.basic.util;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

public class Streams {

	public static String getResourceString(Object anchor, String path) {
		return getResourceString(anchor.getClass(), path);
	}

	public static String getResourceString(Class<?> anchor, String path) {
		try {
			return new String(toByteArray(anchor.getResourceAsStream(path), true), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static byte[] toByteArray(InputStream from, boolean close) {
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] buf = new byte[0x1000];// 4K
			while (true) {
				int r = from.read(buf);
				if (r == -1) {
					break;
				}
				out.write(buf, 0, r);
			}
			return out.toByteArray();
		} catch (Exception e) {
			return null;
		} finally {
			if (close) {
				try {
					from.close();
				} catch (IOException e) {
				}
			}
		}
	}

	public static void copy(InputStream input, OutputStream output) throws IOException {
		byte[] buffer = new byte[4096];
		int n = 0;
		while (-1 != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
		}
	}

	public static void copy(Reader reader, Writer writer) throws IOException {
		char[] buffer = new char[4096];
		int n = 0;
		while (-1 != (n = reader.read(buffer))) {
			writer.write(buffer, 0, n);
		}
	}

	public static void closeQuietly(Closeable closeable) {
		try {
			if (closeable != null) {
				closeable.close();
			}
		} catch (IOException ioe) {
		}
	}
}
