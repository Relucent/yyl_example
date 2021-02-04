package yyl.example.demo.spring.validator;

import java.util.HashMap;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.validation.Errors;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.beanvalidation.CustomValidatorBean;

/**
 * <pre>
 * 依赖包：
 *  javax.validation:validation-api 
 *  org.hibernate:hibernate-validator
 *  org.glassfish:jakarta.el
 * </pre>
 */
public class CustomValidatorExample {

    public static void main(String[] args) {
        CustomValidatorBean validator = new CustomValidatorBean();
        validator.afterPropertiesSet();
        Sample sample = new Sample();
        Errors errors = new MapBindingResult(new HashMap<>(), Sample.class.getName());
        validator.validate(sample, errors);
        for (ObjectError error : errors.getAllErrors()) {
            System.out.println(error.getDefaultMessage());
        }
    }

    public static class Sample {
        @NotNull(message = "主键不能为空")
        public Integer id = 0;
        @NotEmpty(message = "名称不能为空")
        public String name;
    }
}
