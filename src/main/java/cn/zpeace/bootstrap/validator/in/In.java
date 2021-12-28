package cn.zpeace.bootstrap.validator.in;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>校验某个值是否在集合内
 * <p>用于基本类型包装类和 string 类,其他引用类型不保证效果
 * <p>用法: 在字段上@In(allowableValues = {"1","2"})
 * <p>
 * Created on 2021-12-28.
 *
 * @author skiya
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = InValidator.class)
public @interface In {

    /**
     * 默认的错误提示信息
     */
    String message() default "非法可选值";

    /**
     * 是否必须
     */
    boolean required() default true;

    /**
     * 允许通过的可选值
     */
    String[] allowableValues() default {};

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
