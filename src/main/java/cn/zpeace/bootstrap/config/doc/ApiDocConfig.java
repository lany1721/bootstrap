package cn.zpeace.bootstrap.config.doc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author skiya
 * @date Created on 2021-11-25.
 */
@Configuration
@EnableOpenApi
public class ApiDocConfig {

    @Bean
    public Docket defaultApi() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .groupName("default")
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.zpeace.bootstrap"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Bootstrap API")
                .description("Bootstrap RESTFul APIs")
                .termsOfServiceUrl("https://www.xxx.com")
                .contact(new Contact("xx", "https://www.xx.com", "xxx@email.com"))
                .license("Apache 2.0")
                .version("1.0")
                .build();
    }
}
