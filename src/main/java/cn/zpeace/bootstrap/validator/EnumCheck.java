package cn.zpeace.bootstrap.validator;


import cn.zpeace.bootstrap.anno.DocEnum;
import cn.zpeace.bootstrap.constant.BaseEnum;
import org.springframework.core.annotation.AliasFor;

import javax.validation.Constraint;
import javax.validation.Payload;
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
@DocEnum
public @interface EnumCheck {
    /**
     * 默认的错误提示信息
     *
     * @return String
     */
    String message() default "非法的枚举值";

    /**
     * 枚举类对象 必须实现BaseEnum接口
     *
     * @return Class
     */
    @AliasFor(annotation = DocEnum.class, attribute = "clazz")
    Class<? extends BaseEnum> clazz() default BaseEnum.class;

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
