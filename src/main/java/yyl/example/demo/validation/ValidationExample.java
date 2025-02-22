package yyl.example.demo.validation;

import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ValidationExample {

	private static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	private static final Validator validator = factory.getValidator();

	/**
	 * 校验对象，如果有校验错误，返回错误信息
	 * @param object 要校验的对象
	 * @param <T>    泛型
	 * @return 校验结果，如果为空则校验通过，否则返回错误信息
	 */
	public static <T> String validate(T object) {
		Set<ConstraintViolation<T>> violations = validator.validate(object);
		if (violations.isEmpty()) {
			return null; // 校验通过
		}
		return violations.stream().map(v -> v.getPropertyPath() + " " + v.getMessage())
				.collect(Collectors.joining("; "));
	}

	/**
	 * 校验对象，若有异常则抛出 IllegalArgumentException
	 * @param object 要校验的对象
	 * @param <T>    泛型
	 * @throws IllegalArgumentException 如果校验失败，则抛出异常
	 */
	public static <T> void validateAndThrow(T object) {
		String result = validate(object);
		if (result != null) {
			throw new IllegalArgumentException(result);
		}
	}

	static class Example {

		@NotBlank(message = "name Cannot be empty")
		private String name;

		@NotNull(message = "Value cannot be empty")
		@Min(value = 0, message = "value must be greater than or equal to 0")
		@Max(value = 100, message = "value must be less than or equal to 100")
		private Integer value;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Integer getValue() {
			return value;
		}

		public void setValue(Integer value) {
			this.value = value;
		}

	}

	public static void main(String[] args) {
		Example user = new Example();
		user.setName("");
		user.setValue(-10);

		String validationResult = validate(user);
		if (validationResult != null) {
			System.out.println(validationResult);
		}
	}
}
