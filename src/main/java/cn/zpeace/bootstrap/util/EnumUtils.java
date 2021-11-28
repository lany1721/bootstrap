package cn.zpeace.bootstrap.util;

import cn.hutool.core.util.ReflectUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author skiya
 * @date Created on 2021-11-27.
 */
public class EnumUtils {

    public static <T> Map<String, String> getFieldMappings(Class<T> clazz, String keyField, String valueField) {
        T[] enumConstants = clazz.getEnumConstants();
        if (enumConstants == null || enumConstants.length <= 0) {
            return new HashMap<>(4);
        }
        Map<String, String> mapping = new HashMap<>(enumConstants.length * 2);
        for (T enumConstant : enumConstants) {
            Object key = ReflectUtil.getFieldValue(enumConstant, keyField);
            Object value = ReflectUtil.getFieldValue(enumConstant, valueField);
            mapping.put(key.toString(), value.toString());
        }
        return mapping;
    }

    /**
     * 把枚举类的成员属性 转为key-value的形式
     *
     * @param clazz      枚举类
     * @param keyField   作为key的字段名
     * @param valueField 作为value的字段名
     * @return {@link List}<{@link String}>
     * @see EnumUtilsTest#getFieldMappingList()
     */
    public static <T> List<String> getFieldMappingList(Class<T> clazz, String keyField, String valueField) {
        T[] enumConstants = clazz.getEnumConstants();
        if (enumConstants == null || enumConstants.length <= 0) {
            return new ArrayList<>(0);
        }
        List<String> list = new ArrayList<>(enumConstants.length);
        for (T enumConstant : enumConstants) {
            Object key = ReflectUtil.getFieldValue(enumConstant, keyField);
            Object value = ReflectUtil.getFieldValue(enumConstant, valueField);
            list.add(key + "-" + value);
        }
        return list;
    }

}
