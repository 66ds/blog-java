package com.qianbing.blog.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
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
	 * 留言Id
	 */
	@TableId
	private Long stayId;

	/**
	 * 留言用户Id
	 */
	private Long stayUserId;

	/**
	 * 留言内容
	 */
	private String messageContent;

	/**
	 * 留言用户Ip
	 */
	private String stayUserIp;

	/**
	 * 留言时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date messageStayTime;
	/**
	 * 父留言ID
	 */
	private Long parentStayId;

	/**
	 * 留言者系统
	 */
	private String staySys;

	/**
	 * 留言着浏览器
	 */
	private String stayChrome;

	/**
	 * 子留言
	 */
	@TableField(exist = false)
	private StayMessageEntity stayMessageEntity;

	/**
	 * 留言人姓名
	 */
	@TableField(exist = false)
	private String userName;

	/**
	 * 留言人头像
	 */
	@TableField(exist = false)
	private String userImg;


}
