package cn.zpeace.bootstrap.validator.in;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

/**
 * Created on 2021-12-28.
 *
 * @author skiya
 */
public class InValidator implements ConstraintValidator<In, Object> {

    private In in;

    @Override
    public void initialize(In constraintAnnotation) {
        this.in = constraintAnnotation;
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        // 判断是否必须
        if (value == null) {
            return !in.required();
        }
        String[] allowableValues = in.allowableValues();
        if (allowableValues == null || allowableValues.length <= 0) {
            return false;
        }
        // 构建错误提示,把支持的枚举信息返回,禁用掉默认的提示信息
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(in.message() + "(允许可选值:" + Arrays.toString(in.allowableValues()) + ")")
                .addConstraintViolation();
        // 匹配值是否在可选值内
        return Arrays.stream(in.allowableValues()).anyMatch(e -> e.equals(value.toString()));
    }
}
