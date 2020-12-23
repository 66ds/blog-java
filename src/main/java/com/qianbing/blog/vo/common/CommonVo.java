package com.qianbing.blog.vo.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CommonVo {
    private List<Long> ids;

    private List<User> users;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date createTime;

    @Data
    public static class User{

        private Long userId;

        private String userName;
    }
}
