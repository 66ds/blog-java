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
 * @date 2020-11-24 20:58:37
 */
@Data
@TableName("zj_like_comment_user")
public class LikeCommentUserEntity implements Serializable {
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
	 * 评论Id
	 */
	private Long commentId;
	/**
	 * 点赞日期
	 */
	private Date likeDate;

	/**
	 * 是否已读
	 */
	private Long isRead;

}
