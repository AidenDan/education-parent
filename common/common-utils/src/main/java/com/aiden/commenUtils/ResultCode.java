package com.aiden.commenUtils;

/**
 * @author Aiden
 * @version 1.0
 * @description 统一返回结果码处理
 * @date 2021-1-16 11:25:29
 */
public enum ResultCode {
    SUCCESS(true, 20000, "成功"),
    FAIL(false, 20001, "失败");

    ResultCode(boolean status, int code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    private boolean status;
    private int code;
    private String message;

    public boolean getStatus() {
        return status;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
