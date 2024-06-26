package cn.zpeace.bootstrap.validator.en;

import cn.hutool.core.collection.CollUtil;
import cn.zpeace.bootstrap.util.EnumUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;


import java.lang.reflect.Method;

/**
 * @author zpeace
 * @date 2020/9/11
 */
@Slf4j
public class EnumValidator implements ConstraintValidator<Enum, Object> {

    private Enum annotation;

    @Override
    public void initialize(Enum constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        // 判断是否必须
        if (value == null) {
            return !annotation.required();
        }
        // 所有的枚举
        Object[] objects = annotation.clazz().getEnumConstants();
        // 枚举类没有枚举值时，直接返回校验不通过
        if (objects == null || objects.length <= 0) {
            return false;
        }
        // 构建错误提示,把支持的枚举信息返回,禁用掉默认的提示信息
        constraintValidatorContext.disableDefaultConstraintViolation();
        constraintValidatorContext.buildConstraintViolationWithTemplate(annotation.message() + "(可选值:"
                        + CollUtil.join(EnumUtils.getFieldMappingList(annotation.clazz(), "value", "label"), ",") + ")")
                .addConstraintViolation();
        try {
            // 比值的方法
            // 两种 getValue  getLabel  默认使用getValue进行比较
            Method method = annotation.clazz().getMethod(annotation.method());
            // 循环所有枚举值与校验值作比较
            // 相同则校验通过
            for (Object object : objects) {
                if (value.equals(method.invoke(object))) {
                    return true;
                }
            }
        } catch (ReflectiveOperationException e) {
            log.error("枚举校验发生异常", e);
            return false;
        }
        return false;
    }
}
