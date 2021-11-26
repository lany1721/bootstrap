package cn.zpeace.bootstrap.handler;

import cn.zpeace.bootstrap.support.ApiResponse;
import cn.zpeace.bootstrap.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;


/**
 * 响应前 修改
 *
 * @author zpeace
 * @date 2020/9/14
 */
@Slf4j
@RestControllerAdvice(basePackages = "cn.zpeace.bootstrap")
public class BeforeResponseHandler implements ResponseBodyAdvice<Object> {

    /**
     * ApiResponse类型直接返回
     * 不需要进行增强写
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return !returnType.getParameterType().equals(ApiResponse.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        // String类型需要特殊处理
        if (body instanceof String) {
            return JsonUtils.toJsonStr(ApiResponse.ok(body));
        }
        return ApiResponse.ok(body);
    }
}
