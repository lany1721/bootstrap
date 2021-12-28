package cn.zpeace.bootstrap.util;

import cn.zpeace.bootstrap.model.entity.User;
import com.fasterxml.jackson.core.type.TypeReference;
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
}