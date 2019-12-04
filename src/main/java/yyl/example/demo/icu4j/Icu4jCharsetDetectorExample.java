package yyl.example.demo.icu4j;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

import com.ibm.icu.text.CharsetDetector;
import com.ibm.icu.text.CharsetMatch;

import yyl.example.basic.util.IoUtil;

/**
 * 使用ICU4J检测文本编码
 */
public class Icu4jCharsetDetectorExample {

    public static void main(String[] args) throws IOException {
        test("GBK_SAMPLE");
        test("UTF8_SAMPLE");
    }

    private static void test(String path) {
        try {
            byte[] data = IoUtil.getResourceAsByteArray(Icu4jCharsetDetectorExample.class, path);
            String encoding = getEncoding(data);
            System.out.println("path     : " + path);
            System.out.println("encoding : " + encoding);
            System.out.println("content  : " + new String(data, encoding));
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getEncoding(byte[] data) {
        CharsetDetector detector = new CharsetDetector();
        detector.setText(data);
        // 获得全部可能的编码匹配
        CharsetMatch[] matches = detector.detectAll();
        // 根据可信度和优先级进行排序(可信度高的排在前面，可信度相同优先级高的排在前面)
        Arrays.sort(matches, COMPARATOR);
        if (matches == null || matches.length == 0) {
            return DEFAULT_ENCODING;
        }
        return matches[0].getName();
    }

    // 默认编码
    private static final String DEFAULT_ENCODING = "UTF-8";
    // ICU4J 默认是根据
    private static final Comparator<CharsetMatch> COMPARATOR = new Comparator<CharsetMatch>() {
        @Override
        public int compare(CharsetMatch o1, CharsetMatch o2) {
            // 默认是根据Confidence(可信度)进行的排序，但是有时候 Confidence会相等(字符较少的情况)，这时候需要优先考虑最可能的编码
            int compared = o1.compareTo(o2);
            // 无法排序的
            if (compared == 0) {
                int priority1 = getPriority(o1.getName());
                int priority2 = getPriority(o2.getName());
                compared = Integer.compare(priority1, priority2);
            }
            // 逆序(数值大的在前面)
            return -compared;
        }

        // 相同可信度的编码优先级
        private int getPriority(String name) {
            switch (name) {
                case "UTF-8":
                    return 90;
                case "GB18030":
                    return 80;
                case "ISO-8859-1":
                    return 70;
                case "Big5":
                    return 60;
                default:
                    return 0;
            }
        }
    };
}
