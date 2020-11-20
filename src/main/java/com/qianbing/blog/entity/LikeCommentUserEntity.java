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
 * @date 2020-11-20 09:18:33
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
	 * $column.comments
	 */
	private Long userId;
	/**
	 * $column.comments
	 */
	private Long commentId;
	/**
	 * $column.comments
	 */
	private Integer isDelete;
	/**
	 * $column.comments
	 */
	private Date likeDate;

}
