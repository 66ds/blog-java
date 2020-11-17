package com.qianbing.blog.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ArticlesVo  {

    private Long articleId;

    private Long userId;

    private String articleTitle;

    private String articleContent;

    private String articleContentOrigin;

    private Long articleViews;

    private Long articleCommentCount;

    private Date articleDate;

    private Long articleLikeCount;

    private Integer articleType;

    private Integer articleUp;

    private Integer articleSupport;

    private Integer isDelete;

    private String articleIntroduce;

    private List<Long> sortIds;

    private List<String> labelNames;
}
