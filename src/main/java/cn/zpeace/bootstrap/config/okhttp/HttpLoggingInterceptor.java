package cn.zpeace.bootstrap.config.okhttp;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okio.Buffer;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created on 2022-1-28.
 *
 * @author skiya
 */
@Slf4j
public class HttpLoggingInterceptor implements Interceptor {

    @Nonnull
    @Override
    public Response intercept(@Nonnull Chain chain) throws IOException {
        Request request = chain.request();
        String url = request.url().url().toString();
        Headers headers = request.headers();
        RequestBody body = request.body();
        log.info("url:{}, headers:{}, requestBody:{}", url, headers.toMultimap(), getRequestBody(body));
        long startNs = System.nanoTime();
        Response response = chain.proceed(request);
        long duration = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);
        if (!response.isSuccessful()) {
            throw new RuntimeException(String.format("request fail, url:%s, code:%d, message:%s, duration:%d", url, response.code(), response.message(), duration));
        }
        String responseBody = getResponseBody(response.body());
        MediaType mediaType = response.body().contentType();
        log.info("url:{}, responseBody:{}, duration:{}ms", url, responseBody, duration);
        return response.newBuilder().body(ResponseBody.create(responseBody, mediaType)).build();
    }

    public String getRequestBody(RequestBody body) {
        if (body == null) {
            return "";
        }
        try (Buffer buffer = new Buffer()) {
            body.writeTo(buffer);
            return buffer.readUtf8();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getResponseBody(ResponseBody body) {
        if (body == null) {
            return "";
        }
        try {
            return body.string().replaceAll("[\\n|\\r]", "");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
