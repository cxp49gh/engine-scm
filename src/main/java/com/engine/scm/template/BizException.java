package com.engine.scm.template;


import lombok.Getter;

@Getter
public class BizException extends RuntimeException {

    private final String code;
    private final String message;
    private final transient Object detail;

    private BizException(String code, String message, Throwable cause, Object detail) {
        super(message, cause);
        this.code = code;
        this.message = message;
        this.detail = detail;
    }

    /* ========== 静态工厂方法 ========== */

    public static BizException invalid(String message) {
        return new BizException("INVALID_PARAM", message, null, null);
    }

    public static BizException invalid(String message, Throwable cause) {
        return new BizException("INVALID_PARAM", message, cause, null);
    }

    public static BizException invalid(String message, Object detail) {
        return new BizException("INVALID_PARAM", message, null, detail);
    }

    public static BizException notFound(String message) {
        return new BizException("NOT_FOUND", message, null, null);
    }

    public static BizException conflict(String message) {
        return new BizException("CONFLICT", message, null, null);
    }

    public static BizException forbidden(String message) {
        return new BizException("FORBIDDEN", message, null, null);
    }
}

