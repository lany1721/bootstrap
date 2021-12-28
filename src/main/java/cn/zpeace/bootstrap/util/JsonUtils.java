package cn.zpeace.bootstrap.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * jackson 封装工具类
 * 主要是把 原生 ObjectMapper 的异常catch然后重新throw
 *
 * @author skiya
 * @date Created on 2021-11-25.
 */
@Slf4j
public class JsonUtils {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @SneakyThrows
    public static String toJsonStr(Object obj) {
        return MAPPER.writeValueAsString(obj);
    }

    @SneakyThrows
    public static String toJsonPrettyStr(Object obj) {
        return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
    }

    @SneakyThrows
    public static <T> T toBean(String jsonStr, Class<T> type) {
        return MAPPER.readValue(jsonStr, type);
    }

    @SneakyThrows
    public static <T> T toBean(String jsonStr, TypeReference<T> type) {
        return MAPPER.readValue(jsonStr, type);
    }

    /**
     * 转化为 map
     */
    @SneakyThrows
    public static Map<String, Object> toMap(String jsonStr) {
        return MAPPER.readValue(jsonStr, new TypeReference<Map<String, Object>>() {
        });
    }

    /**
     * 转化为 list
     */
    @SneakyThrows
    public static <E> List<E> toList(String jsonStr, Class<E> type) {
        return MAPPER.readValue(jsonStr, MAPPER.getTypeFactory().constructCollectionType(List.class, type));
    }
}
