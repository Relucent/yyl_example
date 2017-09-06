package yyl.example.basic.jdk5;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.toHexString;
import static java.lang.System.out;

/**
 * import 静态成员(Static import) <br>
 * 在Java 5中，import语句得到了增强，以便提供甚至更加强大的减少击键次数功能，虽然一些人争议说这是以可读性为代价的。这种新的特性成为静态导入。<br>
 */
public class TestStaticImport {
	public static void main(String[] args) {
		out.println(MAX_VALUE);
		out.println(toHexString(31));
	}
}
