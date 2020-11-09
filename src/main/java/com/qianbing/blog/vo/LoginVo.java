package com.qianbing.blog.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginVo {
    @NotBlank(message = "手机号不能为空")
    private String userTelephoneNumber;
    @NotBlank(message = "密码不能为空")
    private String userPassword;
}
