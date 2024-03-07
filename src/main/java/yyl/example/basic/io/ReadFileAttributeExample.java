package yyl.example.basic.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * 读取文件属性
 */
public class ReadFileAttributeExample {
	public static void main(String[] args) {
		Path file = Paths.get("src/main/java/yyl/example/basic/io/ReadFileAttributeExample.java");
		try {
			BasicFileAttributes ra = Files.readAttributes(file, BasicFileAttributes.class);
			System.out.println(file.toAbsolutePath());
			System.out.println("     creationTime : " + ra.creationTime());
			System.out.println("   lastAccessTime : " + ra.lastAccessTime());
			System.out.println("             size : " + ra.size());
			System.out.println(" lastModifiedTime : " + ra.lastModifiedTime());
			System.out.println("   isSymbolicLink : " + ra.isSymbolicLink());
			System.out.println("      isDirectory : " + ra.isDirectory());
			System.out.println("    isRegularFile : " + ra.isRegularFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
