package com.qianbing.blog.vo;


import com.qianbing.blog.vo.common.CommonVo;
import lombok.Data;


@Data
public class CommentVo extends CommonVo {

    private Long articleId;

    private String articleTitle;

}
