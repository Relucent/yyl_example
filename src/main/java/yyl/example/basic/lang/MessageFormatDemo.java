package yyl.example.basic.lang;

import java.text.MessageFormat;

/**
 * MessageFormat 提供了字符串格式化方法<br>
 * FormatElement 是占位符，格式有一下几种：<br>
 * { ArgumentIndex } ArgumentIndex是从 0 开始的入参位置索引 <br>
 * { ArgumentIndex , FormatType }<br>
 * { ArgumentIndex , FormatType , FormatStyle }<br>
 * FormatType 指定使用不同的Format子类对入参进行格式化处理，值范围如下：<br>
 * number：调用NumberFormat进行格式化<br>
 * date：调用DateFormat进行格式化<br>
 * time：调用DateFormat进行格式化<br>
 * choice：调用ChoiceFormat进行格式化<br>
 * FormatStyle 设置FormatType中使用的格式化样式，值范围如下：<br>
 * short<br>
 * medium<br>
 * long<br>
 * full<br>
 * integer<br>
 * currency<br>
 * percent<br>
 * SubformatPattern (子格式模式，形如#.##)<br>
 */

public class MessageFormatDemo {

    public static void main(String[] args) {
        // ArgumentIndex必须是非负整数，从 0开始
        printlnFormat("{0},{2},{1}", "A", "B", "C"); // 输出： A,C,B
        // 格式化字符串时 '可以作为转义字符，原样输出后面的内容
        printlnFormat("{0}'{'}", "hello"); // 输出：hello{}
        // 格式化字符串时，两个单引号才表示一个单引号
        printlnFormat("{0},',''", "hello"); // 输出：hello,,'
        // 数字格式化
        printlnFormat("{0,number,#.00}", 1.0); // 输出：1.00
    }

    private static void printlnFormat(String pattern, Object... arguments) {
        String formatted = MessageFormat.format(pattern, arguments);
        System.out.println(formatted);
    }
}
