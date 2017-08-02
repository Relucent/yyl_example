package yyl.example.basic.jdk7.path;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Path基础用法
 */
public class PathTest1 {
	public static void main(String[] args) throws IOException {

		Path path = Paths.get(System.getProperty("user.dir"));

		//File Path的转换
		File file = path.toFile();

		// Path path = file.toPath();

		System.out.println(path);
		System.out.println(file);

		//查看子文件
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
			for (Path children : stream) {
				System.out.println(children);
			}
		}
	}
}
