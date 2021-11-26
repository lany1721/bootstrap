package cn.zpeace.bootstrap.support;

import cn.zpeace.bootstrap.constant.ErrorCodeEnum;
import cn.zpeace.bootstrap.exception.BizException;
import cn.zpeace.bootstrap.util.JsonUtils;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author skiya
 * @date Created on 2021-11-25.
 */
@Schema(description = "接口返回格式统一封装对象")
public class ApiResponse<T> {

    @Schema(description = "状态码", example = "200")
    private Integer status;

    @Schema(description = "消息说明", example = "success")
    private String message;

    @Schema(description = "数据")
    private T data;

    private ApiResponse() {
    }

    public ApiResponse(Integer status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(200, "success", data);
    }

    public static ApiResponse<Void> ok() {
        return ok(null);
    }

    public static <T> ApiResponse<T> fail(BizException e) {
        return new ApiResponse<>(e.getCode(), e.getMessage(), null);
    }

    public static <T> ApiResponse<T> fail(ErrorCodeEnum errorCode) {
        return fail(errorCode, errorCode.message);
    }

    public static <T> ApiResponse<T> fail(ErrorCodeEnum errorCode, String message) {
        return new ApiResponse<>(errorCode.code, message, null);
    }

    @Override
    public String toString() {
        return JsonUtils.toJsonStr(this);
    }

    public Integer getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
