package yyl.example.basic.algorithm.rpn;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * <h3>逆波兰表达式</h3><br>
 * 逆波兰式（Reverse Polish notation，RPN）是波兰逻辑学家J・卢卡西维兹(J・ Lukasiewicz)于1929年首先提出的一种表达式的表示方法 ，也叫后缀表达式。<br>
 * 一般的表达式又称中缀表达式，这种表达式的二元运算符放在两个运算量 之间。而逆波兰表达式又称后缀表达式，这种表达式把运算符放在运算量 后面。<br>
 * 例如： a+b 的逆波兰式表示为 ab+ <br>
 * 它的优势在于只用两种简单操作，入栈和出栈就可以搞定任何普通表达式的运算。其运算方式如下：<br>
 * 如果当前字符为变量或者为数字，则压栈，如果是运算符，则将栈顶两个元素弹出作相应运算，结果再入栈，最后当表达式扫描完后，栈里的就是结果。<br>
 * 
 * <pre>
 * 中缀转后缀
 * 栈:操作符栈
 * 扫描顺序:从左到右
 * 遇到操作数:直接归入
 * 遇到右括号:将栈中操作符依次弹栈，归入，直至遇到左括号，将左括号弹栈，处理完毕
 * 遇到左括号:直接入栈
 * 遇到其他操作符:检测栈顶与当前优先级关系，如果栈顶大于等于当前则出栈，归入，直至栈顶小于当前，并将当前操作符入栈
 * 操作符栈中的优先级:从栈底到栈顶优先级：递增。即：栈顶必须大于下面的
 * </pre>
 */
public class ReversePolishNotation {

    public static void main(String[] args) {
        String expression = "A+B+((C+D*E)+F*G)+H/I-J";
        List<String> infix = splitInfixExpression(expression);
        List<String> rpn = toReversePolishNotation(expression);
        String result = calculateReversePolishNotation(rpn);
        System.out.println(expression);
        System.out.println(infix);
        System.out.println(rpn);
        System.out.println(result);
    }

    /**
     * 拆分中序表达式
     * @param expression 中序表达式
     * @return 拆分后的元素列表
     */
    private static List<String> splitInfixExpression(String expression) {
        List<String> elements = new ArrayList<>();
        for (int i = 0, j = 0, k = expression.length() - 1; i <= k; i++) {
            char c = expression.charAt(i);
            // 运算符
            if (c == '+' || c == '-' || c == '*' || c == '/' || c == '(' || c == ')') {
                if (i != j) {
                    elements.add(expression.substring(j, i));
                }
                elements.add(String.valueOf(c));
                j = i + 1;
            } else if (i == k) {
                elements.add(expression.substring(j));
            }
        }
        return elements;
    }

    /**
     * 检查算术表达术括号是否匹配, 语法是否正确
     * @param expression 算术表达术
     * @return 语法是否正确
     */
    public static boolean isMatch(String expression) {
        // 括号符号栈
        Deque<Character> stack = new LinkedList<>();

        // 遍历字符串
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            // 括号开始
            if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                // 括号结束 ，且栈为空则返回 false
                if (stack.isEmpty()) {
                    return false;
                }
                // 且栈不为空弹出栈顶
                else {
                    stack.pop();
                }
            }
        }
        // 栈为空（所有括号已经闭合）则表达式正确
        return stack.isEmpty();
    }

    /**
     * 解析表达式，获取逆波兰式
     * @param expression 表达式
     * @return 逆波兰式队列
     */
    public static List<String> toReversePolishNotation(String expression) {

        if (!isMatch(expression)) {
            throw new RuntimeException("Expression parentheses do not match!");
        }

        // 逆波兰表达式栈
        List<String> result = new ArrayList<>();
        // 运算符栈
        Deque<String> stack = new LinkedList<>();

        // 遍历表达式元素
        for (String element : splitInfixExpression(expression)) {
            // 运算符
            if (isOperator(element)) {
                // 检测栈顶与当前优先级关系，如果栈顶大于等于当前则出栈并输出；直至栈顶小于当前，并将当前操作符入栈
                while (!stack.isEmpty() && getPriority(stack.peek()) > getPriority(element)) {
                    result.add(stack.pop());
                }
                stack.push(element);
            }
            // 左括号
            else if ("(".equals(element)) {
                stack.push(element);
            }
            // 右括号
            else if (")".equals(element)) {
                // 只要操作符不为左括号则一直输出
                for (String top = stack.pop(); !"(".equals(top); top = stack.pop()) {
                    result.add(top);
                }
            }
            // 运算量
            else {
                result.add(element);
            }
        }
        // 依次弹出栈中剩下的操作符，并输出
        while (!stack.isEmpty()) {
            String top = stack.pop();
            if (!"(".equals(top)) {
                result.add(top);
            }
        }
        return result;
    }

    /**
     * 计算逆波兰式
     * @param rpn 逆波兰式序列
     * @return 逆波兰式结果
     */
    public static String calculateReversePolishNotation(List<String> rpn) {
        Deque<String> stack = new LinkedList<>();
        for (String element : rpn) {
            if (isOperator(element)) {
                String value2 = stack.pop();
                String value1 = stack.pop();
                stack.push("(" + value1 + element + value2 + ")");
            } else {
                // 操作量入栈
                stack.push(element);
            }
        }
        return stack.pop();
    }

    /**
     * 判断是否为操作符 + - * /
     * @param value 字符
     * @return 是否操作符
     */
    private static boolean isOperator(String value) {
        return "+".equals(value) || "-".equals(value) || "*".equals(value) || "/".equals(value);
    }

    /**
     * 获得运算符优先级
     * @param operator 运算符
     * @return 运算符优先级
     */
    private static int getPriority(String operator) {
        switch (operator) {
        case "*":
        case "/":
            return 2;
        case "+":
        case "-":
            return 1;
        case "(":
            return 0;
        default:
            throw new RuntimeException("Unsupported Operator [" + operator + "] ！");
        }
    }
}
