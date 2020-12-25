package com.qianbing.blog.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class WhoDigMeEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private String likeIds;

    private String userIds;

    private String userNames;

    private Date likeDate;

    private Long articleId;

    private String content;

    private Long alias;

}
