package cn.zpeace.bootstrap.util;

import cn.zpeace.bootstrap.model.entity.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

/**
 * @author skiya
 * @date Created on 2021-11-27.
 */
public class JsonUtilsTest {

    private User user;

    private String jsonStr;

    private String listStr;

    @BeforeEach
    public void init() {
        user = new User();
        user.setId(1L);
        user.setName("hello");
        jsonStr = "{\"id\":1,\"name\":\"hello\"}";
        listStr = "[{\"id\":1,\"name\":\"hello\"},{\"id\":2,\"name\":\"world\"}]";
    }

    @Test
    public void toJsonStr() {
        String jsonStr = JsonUtils.toJsonStr(user);
        System.out.println(jsonStr);
    }

    @Test
    public void toJsonPrettyStr() {
        String jsonStr = JsonUtils.toJsonPrettyStr(user);
        System.out.println(jsonStr);
    }

    @Test
    public void toBean() {
        User user = JsonUtils.toBean(jsonStr, User.class);
        System.out.println(user);
    }

    @Test
    public void testToBean() {
        User user = JsonUtils.toBean(jsonStr, new TypeReference<User>() {
        });
        System.out.println(user);
    }

    @Test
    public void toMap() {
        Map<String, Object> map = JsonUtils.toMap(jsonStr);
        System.out.println(map);
    }

    @Test
    public void toList() {
        List<User> users = JsonUtils.toList(listStr, User.class);
        System.out.println(users);
    }

    String jsonObjectStr = "{\"BigIntSupported\":995815895020119788889,\"date\":\"20180322\",\"message\":\"Success !\",\"status\":200,\"city\":\"北京\",\"count\":632,\"data\":{\"shidu\":\"34%\",\"pm25\":73,\"pm10\":91,\"quality\":\"良\",\"wendu\":\"5\",\"ganmao\":\"极少数敏感人群应减少户外活动\",\"yesterday\":{\"date\":\"21日星期三\",\"sunrise\":\"06:19\",\"high\":\"高温 11.0℃\",\"low\":\"低温 1.0℃\",\"sunset\":\"18:26\",\"aqi\":85,\"fx\":\"南风\",\"fl\":\"<3级\",\"type\":\"多云\",\"notice\":\"阴晴之间，谨防紫外线侵扰\"},\"forecast\":[{\"date\":\"22日星期四\",\"sunrise\":\"06:17\",\"high\":\"高温 17.0℃\",\"low\":\"低温 1.0℃\",\"sunset\":\"18:27\",\"aqi\":98,\"fx\":\"西南风\",\"fl\":\"<3级\",\"type\":\"晴\",\"notice\":\"愿你拥有比阳光明媚的心情\"},{\"date\":\"23日星期五\",\"sunrise\":\"06:16\",\"high\":\"高温 18.0℃\",\"low\":\"低温 5.0℃\",\"sunset\":\"18:28\",\"aqi\":118,\"fx\":\"无持续风向\",\"fl\":\"<3级\",\"type\":\"多云\",\"notice\":\"阴晴之间，谨防紫外线侵扰\"},{\"date\":\"24日星期六\",\"sunrise\":\"06:14\",\"high\":\"高温 21.0℃\",\"low\":\"低温 7.0℃\",\"sunset\":\"18:29\",\"aqi\":52,\"fx\":\"西南风\",\"fl\":\"<3级\",\"type\":\"晴\",\"notice\":\"愿你拥有比阳光明媚的心情\"},{\"date\":\"25日星期日\",\"sunrise\":\"06:13\",\"high\":\"高温 22.0℃\",\"low\":\"低温 7.0℃\",\"sunset\":\"18:30\",\"aqi\":71,\"fx\":\"西南风\",\"fl\":\"<3级\",\"type\":\"晴\",\"notice\":\"愿你拥有比阳光明媚的心情\"},{\"date\":\"26日星期一\",\"sunrise\":\"06:11\",\"high\":\"高温 21.0℃\",\"low\":\"低温 8.0℃\",\"sunset\":\"18:31\",\"aqi\":97,\"fx\":\"西南风\",\"fl\":\"<3级\",\"type\":\"多云\",\"notice\":\"阴晴之间，谨防紫外线侵扰\"}]}}";
    String jsonArrayStr = "[{\"date\":\"22日星期四\",\"sunrise\":\"06:17\",\"high\":\"高温 17.0℃\",\"low\":\"低温 1.0℃\",\"sunset\":\"18:27\",\"aqi\":98,\"fx\":\"西南风\",\"fl\":\"<3级\",\"type\":\"晴\",\"notice\":\"愿你拥有比阳光明媚的心情\"},{\"date\":\"23日星期五\",\"sunrise\":\"06:16\",\"high\":\"高温 18.0℃\",\"low\":\"低温 5.0℃\",\"sunset\":\"18:28\",\"aqi\":118,\"fx\":\"无持续风向\",\"fl\":\"<3级\",\"type\":\"多云\",\"notice\":\"阴晴之间，谨防紫外线侵扰\"},{\"date\":\"24日星期六\",\"sunrise\":\"06:14\",\"high\":\"高温 21.0℃\",\"low\":\"低温 7.0℃\",\"sunset\":\"18:29\",\"aqi\":52,\"fx\":\"西南风\",\"fl\":\"<3级\",\"type\":\"晴\",\"notice\":\"愿你拥有比阳光明媚的心情\"},{\"date\":\"25日星期日\",\"sunrise\":\"06:13\",\"high\":\"高温 22.0℃\",\"low\":\"低温 7.0℃\",\"sunset\":\"18:30\",\"aqi\":71,\"fx\":\"西南风\",\"fl\":\"<3级\",\"type\":\"晴\",\"notice\":\"愿你拥有比阳光明媚的心情\"},{\"date\":\"26日星期一\",\"sunrise\":\"06:11\",\"high\":\"高温 21.0℃\",\"low\":\"低温 8.0℃\",\"sunset\":\"18:31\",\"aqi\":97,\"fx\":\"西南风\",\"fl\":\"<3级\",\"type\":\"多云\",\"notice\":\"阴晴之间，谨防紫外线侵扰\"}]";

    @Test
    void toJsonNode() {
        JsonNode objectNode = JsonUtils.toJsonNode(jsonObjectStr);
        System.out.println(objectNode);
        System.out.println(objectNode.get("BigIntSupported").asText(""));
        JsonNode arrayNode = JsonUtils.toJsonNode(jsonArrayStr);
        System.out.println(arrayNode);
    }

    @Test
    void toObjectNode() {
        ObjectNode objectNode = JsonUtils.toObjectNode(jsonObjectStr);
        System.out.println(objectNode);
    }

    @Test
    void toArrayNode() {
        ArrayNode arrayNode = JsonUtils.toArrayNode(jsonArrayStr);
        System.out.println(arrayNode);
    }

    @Test
    void createObjectNode() {
        ObjectNode objectNode = JsonUtils.createObjectNode();
        System.out.println(objectNode);
        Assertions.assertNotNull(objectNode);
    }

    @Test
    void createArrayNode() {
        ArrayNode arrayNode = JsonUtils.createArrayNode();
        System.out.println(arrayNode);
        Assertions.assertNotNull(arrayNode);
    }

    @Test
    void testToBean1() {
        ObjectNode objectNode = JsonUtils.createObjectNode();
        objectNode.put("id", 1L);
        objectNode.put("name", "skiya");
        User user = JsonUtils.toBean(objectNode, User.class);
        System.out.println(user);
    }

    @Test
    void testToBean2() {
        ObjectNode objectNode = JsonUtils.createObjectNode();
        objectNode.put("id", 1L);
        objectNode.put("name", "skiya");
        User user = JsonUtils.toBean(objectNode, new TypeReference<User>() {
        });
        System.out.println(user);
    }

    @Test
    void testToList() {
        ArrayNode arrayNode = JsonUtils.toArrayNode(listStr);
        List<User> users = JsonUtils.toList(arrayNode, User.class);
        System.out.println(users);
    }

    @Test
    void testToJsonNode() {
        JsonNode jsonNode = JsonUtils.toJsonNode(user);
        System.out.println(jsonNode);
    }
}