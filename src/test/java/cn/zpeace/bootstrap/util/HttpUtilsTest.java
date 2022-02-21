package cn.zpeace.bootstrap.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.map.MapUtil;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
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
        responseBody.ifPresent(System.out::println);
    }

    @Test
    void postWithForm() {
        String url = BASE_URL + "/posts";
        HashMap<String, String> formParameters = MapUtil.of("title", "foo");
        formParameters.put("body", "bar");
        Optional<String> responseBody = HttpUtils.post(url, formParameters);
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