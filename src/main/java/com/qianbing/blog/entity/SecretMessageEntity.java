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
 * @date 2020-12-09 14:18:27
 */
@Data
@TableName("zj_secret_message")
public class SecretMessageEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * $column.comments
	 */
	@TableId
	private Long secretId;
	/**
	 * $column.comments
	 */
	private Long sendId;
	/**
	 * $column.comments
	 */
	private Long receiveId;
	/**
	 * $column.comments
	 */
	private String messageTopic;
	/**
	 * $column.comments
	 */
	private String messageContent;
	/**
	 * $column.comments
	 */
	private Date createTime;
	/**
	 * $column.comments
	 */
	private Long isRead;
	/**
	 * $column.comments
	 */
	private Long parentSecretId;

}
