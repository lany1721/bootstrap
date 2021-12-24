package cn.zpeace.bootstrap.util;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>根据 JSR303  Bean Validation规范校验实体类, 类的成员属性需要使用 JSR303的注解</p>
 * <p>依赖hibernate-validate,需要在 pom 中添加:</p>
 * <pre>
 * {@code
 * <dependency>
 * <groupId>org.hibernate</groupId>
 * <artifactId>hibernate-validator</artifactId>
 * <version>6.0.16.Final</version>
 * </dependency>
 * }
 * </pre>
 * <p>如果是 spring 环境中,添加:</p>
 * <pre>
 * {@code
 * <dependency>
 * <groupId>org.springframework.boot</groupId>
 * <artifactId>spring-boot-starter-validation</artifactId>
 * </dependency>
 * }
 * </pre>
 * <p> 用法: {@link cn.zpeace.bootstrap.util.ValidateUtilsTest} </p>
 * Created on 2021-12-22.
 *
 * @author skiya
 */
public class ValidateUtils {

    private ValidateUtils() {
    }

    /**
     * 验证器
     */
    private final static Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * 分组校验实体，返回实体所有属性的校验结果
     *
     * @param entity 被校验的实体对象
     * @param groups 组
     * @return 校验结果
     */
    public static <T> ValidationResult validate(T entity, Class<?>... groups) {
        if (entity == null) {
            throw new IllegalArgumentException("entity can't not be null");
        }
        if (groups == null || groups.length <= 0) {
            throw new IllegalArgumentException("groups can't not be null or empty");
        }
        //解析校验结果
        Set<ConstraintViolation<T>> validateSet = VALIDATOR.validate(entity, groups);
        return buildValidationResult(validateSet);
    }

    /**
     * 校验实体，返回实体所有属性的校验结果
     *
     * @param entity 被校验的实体对象
     * @return 校验结果
     */
    public static <T> ValidationResult validate(T entity) {
        return validate(entity, Default.class);
    }

    /**
     * 分组批量校验指定实体的指定属性是否存在异常
     *
     * @param entity        被校验的实体对象
     * @param propertyNames 属性名集合
     * @param groups        组
     * @return 校验结果
     */
    public static <T> ValidationResult validateProperty(T entity, Collection<String> propertyNames, Class<?>... groups) {
        if (entity == null) {
            throw new IllegalArgumentException("entity can't not be null");
        }
        if (propertyNames == null || propertyNames.size() <= 0) {
            throw new IllegalArgumentException("propertyNames can't not be null or empty");
        }
        if (groups == null || groups.length <= 0) {
            throw new IllegalArgumentException("groups can't not be null or empty");
        }
        Set<ConstraintViolation<T>> validateSet = new HashSet<>();
        for (String propertyName : propertyNames) {
            validateSet.addAll(VALIDATOR.validateProperty(entity, propertyName, groups));
        }
        return buildValidationResult(validateSet);
    }

    /**
     * 批量校验指定实体的指定属性是否存在异常
     *
     * @param entity        被校验的实体对象
     * @param propertyNames 属性名集合
     * @return 校验结果
     */
    public static <T> ValidationResult validateProperty(T entity, Collection<String> propertyNames) {
        return validateProperty(entity, propertyNames, Default.class);
    }

    /**
     * 分组校验指定实体的指定属性是否存在异常
     *
     * @param entity       被校验的实体对象
     * @param propertyName 属性名
     * @param groups       组
     * @return 校验结果
     */
    public static <T> ValidationResult validateProperty(T entity, String propertyName, Class<?>... groups) {
        if (propertyName == null || "".equals(propertyName)) {
            throw new IllegalArgumentException("propertyName can't not be null or blank");
        }
        HashSet<String> propertyNames = new HashSet<>(2);
        propertyNames.add(propertyName);
        return validateProperty(entity, propertyNames, groups);
    }

    /**
     * 校验指定实体的指定属性是否存在异常
     *
     * @param entity       被校验的实体对象
     * @param propertyName 属性名
     * @return 校验结果
     */
    public static <T> ValidationResult validateProperty(T entity, String propertyName) {
        return validateProperty(entity, propertyName, Default.class);
    }

    /**
     * 将异常结果封装返回
     *
     * @param validateSet 检验结果集
     * @param <T>         被校验实体类型
     * @return 校验结果
     */
    private static <T> ValidationResult buildValidationResult(Set<ConstraintViolation<T>> validateSet) {
        ValidationResult validationResult = new ValidationResult();
        if (validateSet != null && validateSet.size() > 0) {
            validationResult.hasErrors = true;
            Map<String, String> errorMsgMap = new HashMap<>(validateSet.size() * 2);
            for (ConstraintViolation<T> constraintViolation : validateSet) {
                errorMsgMap.put(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
            }
            validationResult.errorMessage = errorMsgMap;
        }
        return validationResult;
    }

    static class ValidationResult {

        /**
         * 是否有校验错误信息
         */
        private Boolean hasErrors = false;

        /**
         * k:校验不通过的属性名 - v:校验信息
         */
        private Map<String, String> errorMessage;

        public Boolean hasErrors() {
            return this.hasErrors;
        }

        public Map<String, String> getErrorMessage() {
            return this.errorMessage;
        }

        /**
         * 合并所有错误消息
         *
         * @return 属性名+校验信息;
         */
        public String mergeErrorMessage() {
            if (!this.hasErrors()) {
                return null;
            }
            return this.getErrorMessage().entrySet().stream()
                    .map(i -> i.getKey() + i.getValue())
                    .collect(Collectors.joining(";"));
        }
    }
}
