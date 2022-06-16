package cn.zpeace.bootstrap.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.map.MapUtil;
import cn.zpeace.bootstrap.util.entity.Post;
import okhttp3.FormBody;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * Created on 2022-1-25.
 *
 * @author skiya
 */
public class HttpUtilsTest {

    private final static String BASE_URL = "https://jsonplaceholder.typicode.com";

    @Test
    void get() {
        TimeInterval timer = DateUtil.timer();
        timer.start();
        for (int i = 1; i <= 50; i++) {
            String url = BASE_URL + "/posts/" + i;
            Optional<String> responseBody = HttpUtils.get(url);
            responseBody.ifPresent(System.out::println);
        }
        System.out.println(timer.intervalMs());
    }

    @Test
    void getWithQuery() {
        String url = BASE_URL + "/posts";
        HashMap<String, String> queryParameters = MapUtil.of("userId", "1");
        Optional<String> responseBody = HttpUtils.get(url, queryParameters);
        responseBody.ifPresent(s -> {
            List<Post> posts = JsonUtils.toList(s, Post.class);
            System.out.println(posts);
        });
    }

    @Test
    void postWithForm() {
        String url = BASE_URL + "/posts";
        FormBody formBody = new FormBody.Builder(StandardCharsets.UTF_8).addEncoded("title$", "foo").build();
        Optional<String> responseBody = HttpUtils.post(url, formBody);
        responseBody.ifPresent(System.out::println);
    }

    @Test
    void postWithRequestPayload() {
        String url = BASE_URL + "/posts";
        Object requestPayload = MapUtil.of("title", "foo");
        Optional<String> responseBody = HttpUtils.post(url, requestPayload);
        responseBody.ifPresent(System.out::println);
    }
}