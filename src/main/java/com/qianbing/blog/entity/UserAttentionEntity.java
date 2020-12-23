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
 * @date 2020-11-27 22:57:08
 */
@Data
@TableName("zj_user_attention")
public class UserAttentionEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * $column.comments
	 */
	@TableId
	private Long aId;
	/**
	 * 用户Id
	 */
	private Long userId;

	/**
	 * 被关注Id
	 */
	private Long attentionId;

	/**
	 * 是否已读
	 */
	private Long isRead;

	/**
	 * 关注时间
	 */
	private Date attentionDate;

}
