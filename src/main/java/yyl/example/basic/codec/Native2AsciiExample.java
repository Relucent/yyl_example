package yyl.example.basic.codec;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

/**
 * native2ascii.exe 是Java的一个文件转码工具，可以将内容转为用指定的编码标准文体形式统一的表现出来，它通常位于JDK_home\bin目录下，安装好Java SE后，可在命令行直接使用 native2ascii命令进行转码。<br>
 * 国际化resources.properties文件，中文字符转换为Unicode字符：<br>
 * native2ascii -encoding Unicode resources.properties tmp.properties <br>
 * 注意：Unicode首字母必须大写 <br>
 * 国际化resources.properties文件，Unicode字符转换为中文字符：<br>
 * native2ascii -reverse -encoding GB2312 resources.properties tmp.properties
 */
public class Native2AsciiExample {

    public static void main(String[] args) {
        System.out.println(getUnicodeString("你好世界"));
    }

    public static String getUnicodeString(String value) {
        StringBuilder buffer = new StringBuilder();
        try {
            Process process = Runtime.getRuntime().exec("native2ascii");
            OutputStream output = null;
            try {
                output = process.getOutputStream();
                output.write(value.getBytes());
                output.close();
            } finally {
                output.flush();
            }
            InputStream input = null;
            try {
                InputStreamReader reader = new InputStreamReader(input = process.getInputStream());
                char[] cbuf = new char[1024];
                int len = 0;
                while ((len = reader.read(cbuf)) != -1) {
                    buffer.append(cbuf, 0, len);
                }
            } finally {
                input.close();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }
}
