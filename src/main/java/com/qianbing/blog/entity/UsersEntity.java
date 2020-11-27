package com.qianbing.blog.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;

/**
 * 
 * 
 * @author qianbing
 * @email 1532498760@qq.com
 * @date 2020-11-09 09:12:57
 */
@Data
@ToString
@TableName("zj_users")
public class UsersEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * $column.comments
	 */
	@TableId
	private Long userId;
	/**
	 * $column.comments
	 */
	private String userIp;
	/**
	 * $column.comments
	 */
	private String userName;
	/**
	 * $column.comments
	 */
	@NotBlank(message = "密码不能为空")
	private String userPassword;
	/**
	 * $column.comments
	 */
	private String userEmail;
	/**
	 * $column.comments
	 */
	private String userProfilePhoto;
	/**
	 * $column.comments
	 */
	private Date userRegistrationTime;
	/**
	 * $column.comments
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
	private Date userBirthday;
	/**
	 * $column.comments
	 */
	private Integer userAge;
	/**
	 * $column.comments
	 */
	@NotBlank(message = "手机号不能为空")
	private String userTelephoneNumber;
	/**
	 * $column.comments
	 */
	private String userNickname;
	/**
	 * $column.comments
	 */
	private String userSchool;
	/**
	 * $column.comments
	 */
	private String userBloodType;
	/**
	 * $column.comments
	 */
	private String userSays;
	/**
	 * $column.comments
	 */
	private Integer userLock;
	/**
	 * $column.comments
	 */
	private Integer userFreeze;
	/**
	 * $column.comments
	 */
	private String userDescription;

}
