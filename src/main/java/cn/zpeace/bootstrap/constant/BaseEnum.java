package cn.zpeace.bootstrap.constant;

import java.util.Objects;

/**
 * @author zpeace
 * @date 2020/9/11
 */
public interface BaseEnum {

    /**
     * 取枚举类的值
     *
     * @return 枚举值
     */
    Object getValue();

    /**
     * 获取枚举类字面值
     *
     * @return 枚举字面值
     */
    String getLabel();

    /**
     * 比较value是否相同
     *
     * @param value 枚举值
     * @return boolean
     */
    default boolean equalsValue(Object value) {
        return Objects.equals(getValue(), value);
    }
}
