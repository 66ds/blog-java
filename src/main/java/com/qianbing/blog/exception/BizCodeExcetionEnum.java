package com.qianbing.blog.exception;

public enum BizCodeExcetionEnum {
    UNKNOW_EXCEPTION(10000,"系统位置异常"),
    VALID_EXCEPTION(10001,"参数格式校验失败"),
    PRODUCT_UP_EXCEPTION(10002,"上传异常"),
    SMS_SEND_MORE_EXCEPTION(10003,"操作频繁,请稍后再试"),
    USER_NAME_EXIST_EXCEPTION(10004,"用户名已存在"),
    PHONE_EXIST_EXCEPTON(10005,"手机号已存在"),
    LOGIN_FAIL_EXCEPTON(10006,"登录失败"),
    SMS_SEND_EXCEPTION(10007,"发送验证码异常请联系管理员");
    private int code;

    private String msg;

    BizCodeExcetionEnum(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode(){
        return this.code;
    }

    public String getMsg(){
        return this.msg;
    }
}
