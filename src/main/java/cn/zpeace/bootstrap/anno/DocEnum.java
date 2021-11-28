package cn.zpeace.bootstrap.anno;

import cn.zpeace.bootstrap.constant.BaseEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author skiya
 * @date Created on 2021-11-27.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
public @interface DocEnum {

    /**
     * 枚举类对象 必须实现BaseEnum接口
     *
     * @return Class
     */
    Class<? extends BaseEnum> clazz() default BaseEnum.class;
}
