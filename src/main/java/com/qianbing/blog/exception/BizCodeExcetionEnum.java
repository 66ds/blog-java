package com.qianbing.blog.exception;

public enum BizCodeExcetionEnum {
    SMS_SEND_MORE_EXCEPTION(10003,"操作频繁,请稍后再试"),
    USER_NAME_EXIST_EXCEPTION(10004,"用户名已存在"),
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
