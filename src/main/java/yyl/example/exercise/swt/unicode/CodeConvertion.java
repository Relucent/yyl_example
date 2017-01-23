package yyl.example.exercise.swt.unicode;

/**
 * 功能描述: [特殊字符编码转换]
 * @version $Revision: 0.1 $
 */
public class CodeConvertion {

	/**
	 * 功能描述:[将输入的特殊字符转成UTF-16码]
	 */
	protected static String toUTF(String specialString) {
		if (specialString.equals("") || specialString == null) {
			return "";
		}
		char[] chs = specialString.toCharArray();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < chs.length; i++) {
			if (i != 0) {
				sb = sb.append(";");
			}
			String code = Integer.toHexString((int) chs[i]);
			sb = sb.append(code);
		}
		System.out.println(sb);
		return sb.toString();
	}

	/**
	 * 功能描述:[将UTF-16码转为ISO-8859-1]
	 */
	protected static String toISO(String utfString) {
		String[] str = utfString.split(";");
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < str.length; i++) {
			sb.append((char) Integer.valueOf(str[i], 16).intValue());
		}
		return sb.toString();
	}
}
