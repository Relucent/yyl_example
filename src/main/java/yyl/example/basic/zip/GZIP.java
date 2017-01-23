package yyl.example.basic.zip;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import yyl.example.basic.util.Streams;

public class GZIP {

	public static byte[] zip(byte[] data) throws IOException {
		ByteArrayOutputStream output = null;
		GZIPOutputStream stream = null;
		ByteArrayInputStream input = null;
		try {
			input = new ByteArrayInputStream(data);
			stream = new GZIPOutputStream(output = new ByteArrayOutputStream());
			Streams.copy(input, stream);
			stream.finish();
			return output.toByteArray();
		} finally {
			Streams.closeQuietly(stream);
			Streams.closeQuietly(output);
			Streams.closeQuietly(input);
		}
	}

	public static byte[] unzip(byte[] data) throws IOException {
		ByteArrayOutputStream output = null;
		GZIPInputStream stream = null;
		ByteArrayInputStream input = null;
		try {
			input = new ByteArrayInputStream(data);
			stream = new GZIPInputStream(input);
			output = new ByteArrayOutputStream();
			Streams.copy(stream, output);
			return output.toByteArray();
		} finally {
			Streams.closeQuietly(stream);
			Streams.closeQuietly(output);
			Streams.closeQuietly(input);
		}

	}

	//大文本压缩才有意义
	public static void main(String[] args) throws IOException {

		String value = "东风夜放花千树。更吹落、星如雨。宝马雕车香满路。凤箫声动，玉壶光转，一夜鱼龙舞。";

		byte[] raw = value.getBytes();
		System.out.println("raw.length->" + raw.length);

		byte[] zip = zip(raw);
		System.out.println("zip.length->" + zip.length);

		byte[] unzip = unzip(zip);
		System.out.println("unzip.length->" + unzip.length);

		System.out.println(new String(unzip));
	}
}
