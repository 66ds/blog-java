package com.qianbing.blog.entity.vo;

import lombok.Data;

import java.util.Date;

@Data
public class NoReadCommentEntity {

    private String commentIds;

    private String userIds;

    private String userNames;

    private Date commentDate;

    private Long articleId;

    private String articleTitle;
}
