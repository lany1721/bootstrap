package cn.zpeace.bootstrap.util;

import org.springframework.cglib.beans.BeanMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created on 2022-1-6.
 *
 * @author skiya
 */
public class BeanUtils {

    /**
     * 复制属性
     *
     * @param source 源
     * @param target 目标
     */
    public static void copyProperties(Object source, Object target) {
        org.springframework.beans.BeanUtils.copyProperties(source, target);
    }

    /**
     * 复制对象
     *
     * @param source     源 要复制的对象
     * @param targetType 目标 复制到此对象
     * @param <T>        目标类型
     * @return T
     */
    public static <T> T copy(Object source, Class<T> targetType) {
        if (source == null || targetType == null) {
            return null;
        }
        try {
            T newInstance = targetType.newInstance();
            copyProperties(source, targetType);
            return newInstance;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 复制list
     *
     * @param source     源 要复制的对象
     * @param targetType 目标 复制到此对象
     * @param <E>        源类型
     * @param <T>        目标类型
     * @return List<T>
     */
    public static <E, T> List<T> copyList(List<E> source, Class<T> targetType) {
        if (null == source || source.isEmpty()) {
            return new ArrayList<>();
        }
        return source.stream().map(e -> copy(e, targetType)).collect(Collectors.toList());
    }

    /**
     * 对象 转化为 map
     *
     * @param bean 对象
     * @return map, 属性名(k)-属性值(v)
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> toMap(Object bean) {
        return new HashMap<String, Object>(BeanMap.create(bean));
    }

}
