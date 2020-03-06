package yyl.example.basic.mime;

import javax.activation.FileTypeMap;
import javax.activation.MimetypesFileTypeMap;

/**
 * MIME的全称是Multipurpose Internet Mail Extensions，即多用途互联网邮件扩展。这是HTTP协议中用来定义文档性质及格式的标准。<br>
 */
public class FileTypeMapExample {
	public static void main(String[] args) {
		String filename = System.getProperty("user.dir") + "/src/main/resources/yyl/example/basic/sound/doraemon.mid";
		FileTypeMap map = MimetypesFileTypeMap.getDefaultFileTypeMap();
		String contentType = map.getContentType(filename);
		System.out.println(contentType);
	}
}
