package cn.zpeace.bootstrap.validator.en;


import cn.zpeace.bootstrap.constant.BaseEnum;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * enum检查
 *
 * @author skiya
 * @date 2021-11-03
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = EnumValidator.class)
public @interface Enum {

    /**
     * 默认的错误提示信息
     */
    String message() default "非法的枚举值";

    /**
     * 枚举类对象 必须实现BaseEnum接口
     *
     * @return Class
     */
    Class<? extends BaseEnum<?>> clazz();

    /**
     * 是否必须
     *
     * @return boolean
     */
    boolean required() default true;

    /**
     * 调用枚举类的方法来进行比值
     * 默认调用 BaseEnum.getValue()方法
     *
     * @return String
     */
    String method() default "getValue";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
