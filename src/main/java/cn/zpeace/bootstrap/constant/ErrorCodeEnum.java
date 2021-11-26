package cn.zpeace.bootstrap.constant;

/**
 * @author skiya
 * @date Created on 2021-11-25.
 */
public enum ErrorCodeEnum {
    /**
     * 客户端错误
     */
    BAD_REQUEST(400, "错误请求"),
    UNAUTHORIZED(401, "未认证"),
    FORBIDDEN(403, "禁止访问"),
    NOT_FOUND(404, "资源不存在"),
    METHOD_NOT_ALLOWED(405, "方法不支持"),
    REQUEST_TIMEOUT(408, "请求超时"),
    UNSUPPORTED_MEDIA_TYPE(415, "不支持的媒体类型"),
    TOO_MANY_REQUESTS(429, "请求过于频繁"),

    /**
     * 服务端错误
     */
    INTERNAL_SERVER_ERROR(500, "服务器发生错误");

    /**
     * 状态码
     */
    public final Integer code;

    /**
     * 描述信息
     */
    public final String message;

    ErrorCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
