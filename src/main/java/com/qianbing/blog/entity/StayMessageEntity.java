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
 * @date 2020-12-13 20:46:28
 */
@Data
@TableName("zj_stay_message")
public class StayMessageEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * $column.comments
	 */
	@TableId
	private Long stayId;
	/**
	 * $column.comments
	 */
	private Long stayUserId;
	/**
	 * $column.comments
	 */
	private String messageContent;
	/**
	 * $column.comments
	 */
	private String stayUserIp;
	/**
	 * $column.comments
	 */
	private Integer messageStayTime;
	/**
	 * $column.comments
	 */
	private Long parentStayId;

}
