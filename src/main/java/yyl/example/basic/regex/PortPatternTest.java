package yyl.example.basic.regex;

import java.util.regex.Pattern;

/**
 * 用正则判断端口号范围(1024-65535)<br>
 * 注：这只是正则的演示，实际项目用数字直接判断会更合适一些<br>
 */
public class PortPatternTest {

    // # 1024-65535
    // # ^(102[4-9]|10[3-9]\\d|1[1-9]\\d{2}|[2-9]\\d{3}|[1-5]\\d{4}|6[0-4]\\d{3}|65[0-4]\\d{2}|655[0-2]\\d|6553[0-5])$

    private static final String REGEX = ""//
            + "^"//
            // 4位数,102开头,最后一位4~9的
            + "102[4-9]|" //
            // 4位数,10开头,第三位是3~9的,最后一位任意
            + "10[3-9]\\d|" //
            // 4位数,1开头,第二位是1~9的,最后一位任意
            + "1[1-9]\\d{2}|"//
            // 4位数,第一位2~9,后面任意
            + "[2-9]\\d{3}|"//
            // 5位数,第一位1~5,后面任意
            + "[1-5]\\d{4}|"//
            // 5位数,第一位6,第二位0~4,后面任意
            + "6[0-4]\\d{3}|"//
            // 5位数,第一位6,第二位5,第三位0~4,后面任意
            + "65[0-4]\\d{2}|"//
            // 5位数,第一位6,第二位5,第三位5,第四位0~2,后面任意
            + "655[0-2]\\d|"//
            // 5位数,第一位6,第二位5,第三位5,第四位3,第五位0~5
            + "6553[0-5]"//
            + "$";//

    private static Pattern PATTERN = Pattern.compile(REGEX);


    // 测试
    public static void main(String[] args) {

        System.out.println(PATTERN);

        for (int i = 0; i < 999999; i++) {
            boolean matches = PATTERN.matcher(i + "").matches();

            // 范围： 1024~54435
            if (1024 <= i && i <= 65535) {
                // 如果正则不匹配，应该抛出异常
                if (!matches) {
                    System.err.println(i);
                }
            }
            // 不符合的范围
            else {
                // 如果正则匹配了，应该抛出异常
                if (matches) {
                    System.err.println(i);
                }
            }
        }

    }
}


