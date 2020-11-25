package com.qianbing.blog.vo;

import lombok.Data;

@Data
public class CardVo {

    private String userImg;

    private String userName;

    private Long userId;
    //总文章数
    private Long allArticlesNumber;
    //总访问量
    private Long allArticleViewsNumber;
    //总赞数
    private Long allArticlesLikeNumber;
    //总评论数
    private Long allArticlesCommentsNumber;
}
