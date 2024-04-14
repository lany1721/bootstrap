package cn.zpeace.bootstrap.config.rest;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.Stack;

/**
 * @author zpeace
 */
@Configuration
public class RestConfig {

    @Bean
    public RestTemplate builder(RestTemplateBuilder builder) {

        return builder
                .setReadTimeout(Duration.ofSeconds(10))
                .setConnectTimeout(Duration.ofSeconds(10))
                .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                .build();
    }
}
