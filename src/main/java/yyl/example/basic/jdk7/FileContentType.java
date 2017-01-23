package yyl.example.basic.jdk7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 检测文件的 content-type (Java7 新特性)<br>
 * 测试于JDK7,Window (Window环境其实是根据文件后缀名判断的)
 */
public class FileContentType {

	public static void main(String[] args) throws IOException {
		printContentType("demo.mp3");
		printContentType("demo.mp4");
		printContentType("demo.doc");
		printContentType("demo.zip");
		printContentType("demo.rar");
	}

	/**
	 * 打印文件内容类型
	 * @param filename 文件名称
	 */
	private static void printContentType(String filename) {
		Path path = Paths.get(filename);
		String contentType = null;
		try {
			contentType = Files.probeContentType(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("File content type is : " + contentType);
	}
}
