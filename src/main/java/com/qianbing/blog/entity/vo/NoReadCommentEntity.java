package com.qianbing.blog.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class NoReadCommentEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private String commentIds;

    private String userIds;

    private String userNames;

    private Date commentDate;

    private Long articleId;

    private String articleTitle;
}
