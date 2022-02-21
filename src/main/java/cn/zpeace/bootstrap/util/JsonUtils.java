package cn.zpeace.bootstrap.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
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

    public static ObjectNode createObjectNode() {
        return MAPPER.createObjectNode();
    }

    public static ArrayNode createArrayNode() {
        return MAPPER.createArrayNode();
    }

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

    public static <T> T toBean(ObjectNode objectNode, Class<T> type) {
        return MAPPER.convertValue(objectNode, type);
    }

    public static <T> T toBean(ObjectNode objectNode, TypeReference<T> type) {
        return MAPPER.convertValue(objectNode, type);
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

    public static <E> List<E> toList(ArrayNode arrayNode, Class<E> type) {
        return MAPPER.convertValue(arrayNode, MAPPER.getTypeFactory().constructCollectionType(List.class, type));
    }

    @SneakyThrows
    public static JsonNode toJsonNode(String jsonStr) {
        return MAPPER.readTree(jsonStr);
    }

    public static JsonNode toJsonNode(Object obj) {
        return MAPPER.valueToTree(obj);
    }

    /**
     * 需要对象格式 json,否则抛出ClassCastException
     */
    public static ObjectNode toObjectNode(String jsonStr) {
        return (ObjectNode) toJsonNode(jsonStr);
    }

    /**
     * 需要数组格式 json,否则抛出ClassCastException
     */
    public static ArrayNode toArrayNode(String jsonStr) {
        return (ArrayNode) toJsonNode(jsonStr);
    }
}
