package com.qianbing.blog.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author qianbing
 * @email 1532498760@qq.com
 * @date 2020-11-10 15:44:24
 */
@Data
@TableName("zj_articles")
public class ArticlesEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * $column.comments
	 */
	@TableId
	private Long articleId;
	/**
	 * $column.comments
	 */
	private Long userId;
	/**
	 * $column.comments
	 */
	private String articleTitle;
	/**
	 * $column.comments
	 */
	private String articleContent;
	/**
	 * $column.comments
	 */
	private Long articleViews;
	/**
	 * $column.comments
	 */
	private Long articleCommentCount;
	/**
	 * $column.comments
	 */
	private Date articleDate;
	/**
	 * $column.comments
	 */
	private Long articleLikeCount;
	/**
	 * $column.comments
	 */
	private Integer articleType;
	/**
	 * $column.comments
	 */
	private Integer articleUp;
	/**
	 * $column.comments
	 */
	private Integer articleSupport;

}
