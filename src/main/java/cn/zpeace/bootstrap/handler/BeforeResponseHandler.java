package cn.zpeace.bootstrap.handler;

import cn.zpeace.bootstrap.support.ApiResponse;
import cn.zpeace.bootstrap.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ResponseBody;
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
     * 只有被{@link ResponseBody}注解才进行包装, 并且ApiResponse类型不包装
     * 不需要进行增强写
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return this.isAnnotatedByResponseBody(returnType) && !returnType.getParameterType().equals(ApiResponse.class);
    }

    /**
     * 是否被{@link ResponseBody}注解
     */
    public boolean isAnnotatedByResponseBody(MethodParameter returnType) {
        return AnnotatedElementUtils.hasAnnotation(returnType.getContainingClass(), ResponseBody.class)
                || returnType.hasMethodAnnotation(ResponseBody.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        // String类型需要特殊处理
        if (body instanceof String) {
            // String 类型 contentType 会被设置为 text/plain, 这里重新设置为 application/json
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            return JsonUtils.toJsonStr(ApiResponse.ok(body));
        }
        return ApiResponse.ok(body);
    }
}
