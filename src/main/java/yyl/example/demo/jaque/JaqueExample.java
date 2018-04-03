package yyl.example.demo.jaque;

import java.io.Serializable;
import java.lang.reflect.Member;
import java.util.function.Function;

import com.trigersoft.jaque.expression.Expression;
import com.trigersoft.jaque.expression.InvocationExpression;
import com.trigersoft.jaque.expression.LambdaExpression;
import com.trigersoft.jaque.expression.MemberExpression;
import com.trigersoft.jaque.expression.UnaryExpression;

/**
 * 可以通过JaQue 获得查看Lambda表达式的语法信息<br>
 */
public class JaqueExample {

	public static void main(String[] args) {
		JaqueExample.<Bean>property(Bean::getName);
		JaqueExample.<Bean>property(obj -> obj.getName() == "java");
		JaqueExample.<Bean>property(obj -> obj.getValue() == "sun" || obj.getValue() == "orcale");
	}

	public static <T> void property(Property<T, ?> propertyRef) {
		LambdaExpression<Function<T, ?>> parsed = LambdaExpression.parse(propertyRef);
		// Use parsed Expression Tree...
		Expression body = parsed.getBody();
		Expression methodCall = body;

		System.out.println("body:\n" + body);

		// remove casts
		while (methodCall instanceof UnaryExpression) {
			methodCall = ((UnaryExpression) methodCall).getFirst();
		}
		// checks are omitted for brevity
		MemberExpression invexp = (MemberExpression) ((InvocationExpression) methodCall).getTarget();
		Member member = invexp.getMember();
		System.out.println("member:\n" + member);
		System.out.println();
	}

	public static interface Property<T, R> extends Function<T, R>, Serializable {
	}

	public static class Bean {
		private Long id;
		private String name;
		private String value;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}
}
