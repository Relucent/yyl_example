package yyl.example.basic.jdk7.path;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * 查看文件信息
 */
public class PathTest2 {
	public static void main(String[] args) throws IOException {
		Path path = Paths.get(System.getProperty("user.dir"));
		try {
			BasicFileAttributes attributes = Files.readAttributes(path, BasicFileAttributes.class);
			System.out.println("Creation Time: " + attributes.creationTime());
			System.out.println("Last Accessed Time: " + attributes.lastAccessTime());
			System.out.println("Last Modified Time: " + attributes.lastModifiedTime());
			System.out.println("File Key: " + attributes.fileKey());
			System.out.println("Directory: " + attributes.isDirectory());
			System.out.println("Other Type of File: " + attributes.isOther());
			System.out.println("Regular File: " + attributes.isRegularFile());
			System.out.println("Symbolic File: " + attributes.isSymbolicLink());
			System.out.println("Size: " + attributes.size());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
