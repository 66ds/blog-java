package com.qianbing.blog.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

/**
 * 
 * 
 * @author qianbing
 * @email 1532498760@qq.com
 * @date 2020-12-09 14:18:27
 */
@Data
@ToString
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
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
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
