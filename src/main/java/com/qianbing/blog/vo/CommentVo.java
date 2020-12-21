package com.qianbing.blog.vo;


import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class CommentVo {

    private List<Long> commentIds;

    private List<User> users;

    private Date createTime;

    private Long articleId;

    private String articleTitle;

    @Data
    public static class User{

        private Long userId;

        private String userName;
    }

}
