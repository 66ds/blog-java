package com.qianbing.blog.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RegisterVo {
    @NotBlank(message = "手机号不能为空")
    private String userTelephoneNumber;
    private String code;
    @NotBlank(message = "密码不能为空")
    private String userPassword;
}
