package cn.zpeace.bootstrap.exception;

import cn.zpeace.bootstrap.constant.ErrorCodeEnum;

/**
 * @author skiya
 * @date Created in 2021/7/26.
 */
public abstract class BizException extends RuntimeException {

    /**
     * 异常错误编码
     */
    private int code;

    /**
     * 异常信息
     */
    private String message;

    private BizException() {
    }

    public BizException(ErrorCodeEnum errorEnum) {
        this.code = errorEnum.code;
        this.message = errorEnum.message;
    }

    public BizException(ErrorCodeEnum errorEnum, String errorMessage) {
        this.code = errorEnum.code;
        this.message = errorMessage;
    }

    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
