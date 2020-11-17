package yyl.example.demo.spring.spel;

import java.util.HashMap;
import java.util.Map;

import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * Spring Expression Language（缩写为SpEL）是一种强大的表达式语言。<br>
 * 在Spring产品组合中，它是表达式计算的基础。它支持在运行时查询和操作对象图，它可以与基于XML和基于注解的Spring配置还有bean定义一起使用，能够在运行时动态分配值。<br>
 */
public class SpringExpressionLanguageExample {

    public static void main(String[] args) {
        ExpressionParser parser = new SpelExpressionParser();
        Environment environment = getEnvironment();
        RootBean root = new RootBean(environment);
        StandardEvaluationContext context = new StandardEvaluationContext(root);
        context.setVariable("environment", environment);
        Expression expression = parser.parseExpression("environment.getProperty('demo.message')");
        Object value = expression.getValue(context);
        System.out.println(value);
    }

    private static Environment getEnvironment() {
        Map<String, Object> source = new HashMap<>();
        source.put("demo.message", "hello world");
        StandardEnvironment environment = new StandardEnvironment();
        environment.getPropertySources().addLast(new MapPropertySource("default", source));
        return environment;
    }

    static class RootBean {
        private Environment environment;

        public RootBean(Environment environment) {
            this.environment = environment;
        }

        public Environment getEnvironment() {
            return environment;
        }

        public void setEnvironment(Environment environment) {
            this.environment = environment;
        }
    }
}
