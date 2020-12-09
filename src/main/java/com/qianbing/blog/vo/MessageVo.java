package com.qianbing.blog.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class MessageVo {
    private String userImg;
    private String userName;
    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date createTime;
    private Long userId;
    private boolean isAttention;//是否关注
}
