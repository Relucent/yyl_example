package yyl.example.basic.codec;

public class Base64Test {

	public static void main(String[] args) {

		Base64 tools1 = new Java8Base64();
		Base64 tools2 = new Java67Base64();

		String original = "hello base64";
		String coded = tools1.encode(original.getBytes());
		System.out.println(coded);
		String result = new String(tools2.decode(coded));
		System.out.println(result);
	}

	static interface Base64 {
		public String encode(byte[] value);

		public byte[] decode(String value);
	}

	static class Java8Base64 implements Base64 {
		@Override
		public String encode(byte[] value) {
			return java.util.Base64.getEncoder().encodeToString(value);
		}

		@Override
		public byte[] decode(String value) {
			return java.util.Base64.getDecoder().decode(value);
		}
	}

	static class Java67Base64 implements Base64 {
		public String encode(byte[] data) {
			return javax.xml.bind.DatatypeConverter.printBase64Binary(data);
		}

		public byte[] decode(String base64) {
			return javax.xml.bind.DatatypeConverter.parseBase64Binary(base64);
		}
	}
}
