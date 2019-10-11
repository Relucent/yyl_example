package yyl.example.basic.format;

/**
 * 自定义的Format，仿照 Slf4j的Format方式
 */
public class CustomFormatDemo {

    public static void main(String[] args) {
        System.out.println(format("^{},{},\\{}$", "A0", "B1", "C2"));
        System.out.println(format("^{},{},{}$", "A0", "B1", "C2"));
        System.out.println(format("^{},{}, xx $", "A0", "B1", "C2"));
    }

    /**
     * 格式化文本, {} 表示占位符<br>
     * 此方法只是简单将占位符 {} 按照顺序替换为参数<br>
     * 如果想输出 {} 使用 \\转义 { 即可，如果想输出 {} 之前的 \ 使用双转义符 \\\\ 即可<br>
     * 例：<br>
     * 通常使用：format("this is {} for {}", "a", "b") =》 this is a for b<br>
     * 转义{}： format("this is \\{} for {}", "a", "b") =》 this is \{} for a<br>
     * 转义\： format("this is \\\\{} for {}", "a", "b") =》 this is \a for b<br>
     * @param format 文本模板，被替换的部分用 {} 表示
     * @param args 参数值
     * @return 格式化后的文本
     */
    public static String format(final String format, final Object... args) {
        if (null == format) {
            return null;
        }
        if ((format == null) || args == null) {
            return format;
        }
        final int templateLength = format.length();

        StringBuilder buider = new StringBuilder(templateLength + args.length * 31);

        int handledPosition = 0;// 记录已经处理到的位置
        int delimIndex;// 占位符所在位置
        for (int argIndex = 0; argIndex < args.length; argIndex++) {
            delimIndex = format.indexOf("{}", handledPosition);
            if (delimIndex == -1) {// 剩余部分无占位符
                if (handledPosition == 0) { // 不带占位符的模板直接返回
                    return format;
                }
                // 字符串模板剩余部分不再包含占位符，加入剩余部分后返回结果
                buider.append(format, handledPosition, templateLength);
                return buider.toString();
            }

            // 转义符
            if (delimIndex > 0 && format.charAt(delimIndex - 1) == '\\') {// 转义符
                if (delimIndex > 1 && format.charAt(delimIndex - 2) == '\\') {// 双转义符
                    // 转义符之前还有一个转义符，占位符依旧有效
                    buider.append(format, handledPosition, delimIndex - 1);
                    buider.append(args[argIndex]);
                    handledPosition = delimIndex + 2;
                } else {
                    // 占位符被转义
                    argIndex--;
                    buider.append(format, handledPosition, delimIndex - 1);
                    buider.append("{");
                    handledPosition = delimIndex + 1;
                }
            } else {// 正常占位符
                buider.append(format, handledPosition, delimIndex);
                buider.append(args[argIndex]);
                handledPosition = delimIndex + 2;
            }
        }

        // append the characters following the last {} pair.
        // 加入最后一个占位符后所有的字符
        buider.append(format, handledPosition, format.length());

        return buider.toString();
    }
}
