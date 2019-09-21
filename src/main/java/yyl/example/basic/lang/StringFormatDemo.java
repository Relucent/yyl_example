package yyl.example.basic.lang;

import java.util.Date;
import java.util.IllegalFormatFlagsException;

/**
 * 从 Java 5.0 开始，String 类新增了一个字符串格式化方法 format()<br>
 * 占位符 "%" 后面的字母决定了其接受的实际参数的类型。占位符类型有下面几种：<br>
 * %a 浮点数 以16进制输出浮点数 <br>
 * %b %B 任意值 如果参数为 null 则输出 false，否则输出 true <br>
 * %c %C 字符或整数 输出对应的 Unicode 字符 <br>
 * %d 整数 对整数进行格式化输出 <br>
 * %e %E 浮点数 以科学记数法输出浮点数 <br>
 * %f 浮点数 对浮点数进行格式化输出 <br>
 * %g %G 浮点数 以条件来决定是否以科学记数法方式输出浮点数 <br>
 * %h %H 任意值 以 16 进制输出参数的 hashCode() 返回值 <br>
 * %o 整数 以8进制输出整数 <br>
 * %s %S 字符串 对字符串进行格式化输出 <br>
 * %t 日期时间 对日期时间进行格式化输出 <br>
 * %x %X 整数 以16进制输出整数 <br>
 * %n 无 换行符 <br>
 * %% 无 百分号本身 <br>
 * 大写字母表示输出的字母都为大写，占位符可以指定某个位置的参数，格式为 %n$。例如 %2$d 表示第二个整形参数。注意这里的 n 是 1 开始而不是 0 开始<br>
 * 日期格式化<br>
 * %n$tX 其中 "t" 表示日期时间，其中 X 表示取时间中的哪一部分。<br>
 * X 的可选值：Y=年；m=月；d=日；H=时；M=分；S=秒；L=毫秒；A=星期几（名称）；B=月份名称<br>
 * @see String#format(String, Object...)
 * @see https://docs.oracle.com/javase/8/docs/api/java/util/Formatter.html
 */
public class StringFormatDemo {

    public static void main(String[] args) {

        // 补齐空格并右对齐：
        printlnFormat("%10s, World", "Hello"); // 输出 " Hello, world"
        printlnFormat("%8d", 123); // 输出 " 123"
        printlnFormat("%8s", 123); // 输出 " 123"

        // 补齐空格并左对齐：
        printlnFormat("%-10s, world", "Hello"); // 输出 "Hello , world"
        printlnFormat("%-8d", 123); // 输出 "123 "

        // 补齐 0 并对齐（仅对数字有效）
        printlnFormat("%08d", 123); // 输出 "00000123"
        printlnFormat("%-08d", 123); // 错误！不允许在右边补齐 0

        // 输出最多N个字符
        printlnFormat("%.5s", "Hello, world"); // 输出 "Hello"
        printlnFormat("%.5s...", "Hello, world"); // 输出 "Hello..."
        printlnFormat("%10.5s...", "Hello, world"); // 输出 " Hello..."

        // 输出逗号分隔数字
        printlnFormat("%,d", 1234567); // 输出 "1,234,567"

        // 注意 "%1$10tH" 中的 10 同样表示空格补齐 10 位并右对齐
        printlnFormat("Now is %1$10tH:%1$tM:%1$tS, %1$tA", new Date());
        // Y年，m月，d日 ，H时，M分，S秒，L毫秒
        printlnFormat("%1$tY-%1$tm-%1$td %1$tH-%1$tM-%1$tM %1$tL", new Date());
    }

    private static void printlnFormat(String format, Object... args) {
        try {
            String formatted = String.format(format, args);
            System.out.println(formatted);
        } catch (IllegalFormatFlagsException e) {
            System.out.println(e.toString());
        }
    }

}
