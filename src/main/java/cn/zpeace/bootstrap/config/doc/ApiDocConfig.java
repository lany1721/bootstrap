package cn.zpeace.bootstrap.config.doc;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author skiya
 * @date Created on 2021-11-25.
 */
@Configuration
public class ApiDocConfig {

    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group("bootstrap-admin")
                .pathsToMatch("/**")
                .packagesToScan("cn.zpeace.bootstrap")
                .addOpenApiMethodFilter(method -> AnnotatedElementUtils.isAnnotated(method, RequestMapping.class))
                .build();
    }

    @Bean
    public OpenAPI info() {
        return new OpenAPI()
                .info(new Info().title("Bootstrap API")
                        .description("Bootstrap RESTFul APIs")
                        .version("1.0.0"));
    }
}
