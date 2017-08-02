package yyl.example.basic.jdk7.path;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * 遍历目录树
 */
public class PathTest3 {
	public static void main(String[] args) throws IOException {
		Path path = Paths.get(System.getProperty("user.dir"));
		Files.walkFileTree(path, new SimpleFileVisitor<Path>() {

			@Override
			public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
				System.out.println("Directory:-> " + dir);
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				if (file.toUri().getPath().endsWith(".java")) {
					System.out.println("File:-> " + file);
				}
				return FileVisitResult.CONTINUE;
			}
		});
	}
}
