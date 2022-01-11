package cn.zpeace.bootstrap.handler;

import cn.zpeace.bootstrap.constant.ErrorCodeEnum;
import cn.zpeace.bootstrap.support.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;


/**
 * 全局异常处理程序
 *
 * @author skiya
 * @date Created on 2021-11-25.
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 参数解析异常，一般是json的格式有误
     * HTTP status 400
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ApiResponse<Void> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.warn(e.getMessage());
        return ApiResponse.fail(ErrorCodeEnum.BAD_REQUEST, "数据格式有误，无法解析");
    }

    /**
     * 当数据校验失败的时候，会抛出异常:
     * ConstraintViolationException、
     * MethodArgumentNotValidException、
     * BindException
     * HTTP status 400
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ApiResponse<Void> handleConstraintViolationException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        StringBuilder sb = new StringBuilder();
        for (ConstraintViolation<?> violation : constraintViolations) {
            sb.append(violation.getMessageTemplate()).append(";");
        }
        return ApiResponse.fail(ErrorCodeEnum.BAD_REQUEST, sb.toString());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldError> errors = e.getFieldErrors();
        StringBuilder sb = new StringBuilder();
        for (FieldError error : errors) {
            sb.append(error.getField()).append(error.getDefaultMessage()).append(";");
        }
        return ApiResponse.fail(ErrorCodeEnum.BAD_REQUEST, sb.toString());
    }

    @ExceptionHandler(BindException.class)
    public ApiResponse<Void> handlerBindingException(BindException e) {
        List<FieldError> errors = e.getBindingResult().getFieldErrors();
        StringBuilder sb = new StringBuilder();
        for (FieldError error : errors) {
            sb.append(error.getField()).append(error.getDefaultMessage()).append(";");
        }
        return ApiResponse.fail(ErrorCodeEnum.BAD_REQUEST, sb.toString());
    }

    /**
     * 被@RequestParam(required = true)注解的参数 为空时抛出此异常
     * HTTP status 400
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ApiResponse<Void> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        return ApiResponse.fail(ErrorCodeEnum.BAD_REQUEST, String.format("%s不能为空", e.getParameterName()));
    }

    /**
     * 请求url不支持该请求方法
     * HTTP status 405
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ApiResponse<Void> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return ApiResponse.fail(ErrorCodeEnum.METHOD_NOT_ALLOWED, e.getMessage() + ";" + "支持的方法有:" + Arrays.toString(e.getSupportedMethods()));
    }

    /**
     * 不支持的媒体类型
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ApiResponse<Void> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        String sb = "不支持媒体类型:" + e.getContentType() + ";" +
                "支持的媒体类型有:" + e.getSupportedMediaTypes() + ";";
        return ApiResponse.fail(ErrorCodeEnum.UNSUPPORTED_MEDIA_TYPE, sb);
    }

    /**
     * 拦截其他异常，意味着系统发生了不可预知的异常
     *
     * @param e e
     * @return {@link ApiResponse<Void>}
     */
    @ExceptionHandler(Exception.class)
    public ApiResponse<Void> handleOtherException(Exception e) {
        log.error("服务器发生异常", e);
        return ApiResponse.fail(ErrorCodeEnum.INTERNAL_SERVER_ERROR);
    }

}
