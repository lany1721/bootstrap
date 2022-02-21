package cn.zpeace.bootstrap.util;

import cn.hutool.core.map.MapUtil;
import cn.zpeace.bootstrap.config.okhttp.HttpLoggingInterceptor;
import okhttp3.*;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * Created on 2022-1-25.
 *
 * @author skiya
 */

public class HttpUtils {

    private static final OkHttpClient CLIENT = new OkHttpClient.Builder()
            .addInterceptor(new HttpLoggingInterceptor())
            .connectionPool(new ConnectionPool(50, 2, TimeUnit.MINUTES))
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build();

    private static final MediaType APPLICATION_JSON = MediaType.parse("application/json; charset=utf-8");

    public static Optional<String> get(String url) {
        return get(url, null, null);
    }

    public static Optional<String> get(String url, Map<String, String> queryParameters) {
        return get(url, queryParameters, null);
    }

    public static Optional<String> get(String url, Map<String, String> queryParameters, Map<String, String> headers) {
        String queryString = MapUtil.join(queryParameters, "&", "=", true);
        if (!queryString.trim().isEmpty()) {
            url = url + "?" + queryString;
        }
        Request.Builder builder = new Request.Builder();
        if (headers != null && !headers.isEmpty()) {
            builder.headers(Headers.of(headers));
        }
        Request request = builder.get().url(url).build();
        try (Response response = CLIENT.newCall(request).execute()) {
            return resolve(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Optional<String> post(String url) {
        Request request = new Request.Builder().url(url).method("POST", null).build();
        try (Response response = CLIENT.newCall(request).execute()) {
            return resolve(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Optional<String> post(String url, Map<String, String> formParameters) {
        return post(url, formParameters, null);
    }

    public static Optional<String> post(String url, Map<String, String> formParameters, Map<String, String> headers) {
        Assert.notNull(url, "url 不能为 null");
        Assert.notNull(formParameters, "formParameters 不能为 null");
        List<String> names = new ArrayList<>();
        List<String> values = new ArrayList<>();
        formParameters.forEach((k, v) -> {
            names.add(k);
            values.add(v);
        });
        FormBody formBody = new FormBody(names, values);
        Request.Builder builder = new Request.Builder();
        if (headers != null && !headers.isEmpty()) {
            builder.headers(Headers.of(headers));
        }
        Request request = builder
                .post(formBody)
                .url(url)
                .build();
        try (Response response = CLIENT.newCall(request).execute()) {
            return resolve(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Optional<String> post(String url, Object requestPayload) {
        return post(url, requestPayload, null);
    }

    public static Optional<String> post(String url, Object requestPayload, Map<String, String> headers) {
        Assert.notNull(url, "url 不能为 null");
        Assert.notNull(requestPayload, "requestPayload 不能为 null");
        Request.Builder builder = new Request.Builder();
        if (headers != null && !headers.isEmpty()) {
            builder.headers(Headers.of(headers));
        }
        RequestBody body = RequestBody.create(JsonUtils.toJsonStr(requestPayload), APPLICATION_JSON);
        Request request = builder
                .post(body)
                .url(url)
                .build();
        try (Response response = CLIENT.newCall(request).execute()) {
            return resolve(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Optional<String> resolve(Response response) {
        return Optional.ofNullable(response.body()).map(r -> {
            try {
                return r.string();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
