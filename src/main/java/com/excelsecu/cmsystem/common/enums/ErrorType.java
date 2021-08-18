package com.excelsecu.cmsystem.common.enums;

// 错误码暂时按照abc-def 编码，前三位是功能模块， 后三位错误编号
// 101：用户登录模块
// 102：用户管理模块
public enum ErrorType {
    SUCCESS(0,"成功"),
    FAILED(101000,"失败"),
    ACCOUNT_EXISTED(101001, "账户名已存在"),
    ACCOUNT_MISSING(101002, "账户名不存在"),
    ACCOUNT_INVALID(101003, "账号未生效，请联系管理员"),
    TOKEN_INVALID(101004, "无效的登录凭证"),
    LOGIN_FAILED(101005, "用户名或密码错误"),
    USER_ID_INVALID(101006, "无效的用户ID"),
    IS_SUPER_ADMIN(101007, "超级管理员不能被禁用或删除"),
    PARAM_INVALID(101009, "参数无效"),
    PARAM_TYPE(101010, "参数类型不正确"),
    WRONG_PASSWORD(101011, "密码错误"),
    SAME_PASSWORD(101012, "新旧密码相同"),;

    private Integer code;
    private String message;

    ErrorType(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
