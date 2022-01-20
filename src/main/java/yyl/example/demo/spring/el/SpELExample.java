package yyl.example.demo.spring.el;

import java.util.ArrayList;

import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * SpEL（Spring Expression Language），Spring表达式语言
 */
public class SpELExample {

    public static void main(String[] args) {

        // 创建ExpressionParser解析表达式
        ExpressionParser parser = new SpelExpressionParser();

        // 虚拟的容器 EvaluationContext (默认是 ApplicationContext)
        StandardEvaluationContext ctx = new StandardEvaluationContext();

        // 向容器内添加变量
        ctx.setVariable("result", new ArrayList<>());

        // 根对象，引用它的属性时，可以不加前缀(一个EvaluationContext只能有一个)
        ctx.setRootObject("hello");

        // 根据SpEL表达式获取所需的值
        Expression expression1 = parser.parseExpression("#result.toString()");
        System.out.println(expression1.getValue(ctx));

        // 根据SpEL表达式获取所需的值
        Expression expression2 = parser.parseExpression("toString()");
        System.out.println(expression2.getValue(ctx));
    }
}
