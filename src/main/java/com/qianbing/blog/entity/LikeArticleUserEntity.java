package com.qianbing.blog.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author qianbing
 * @email 1532498760@qq.com
 * @date 2020-11-20 09:51:18
 */
@Data
@TableName("zj_like_article_user")
public class LikeArticleUserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * $column.comments
	 */
	@TableId
	private Long likeId;
	/**
	 * 用户Id
	 */
	private Long userId;
	/**
	 * 文章id
	 */
	private Long articleId;

	/**
	 * 点赞日期
	 */
	private Date likeDate;

	/**
	 * 是否已读
	 */
	private Long isRead;

}
