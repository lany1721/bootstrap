package cn.zpeace.bootstrap.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


/**
 * Created on 2022-1-6.
 *
 * @author skiya
 */
public class ServletUtils {

    /**
     * 通过 spring request context 获取HttpServletRequest
     * 需要在 http 请求域中使用
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return servletRequestAttributes == null ? null : servletRequestAttributes.getRequest();
    }
}
