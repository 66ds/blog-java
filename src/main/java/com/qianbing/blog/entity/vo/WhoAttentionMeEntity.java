package com.qianbing.blog.entity.vo;

import lombok.Data;

import java.util.Date;

@Data
public class WhoAttentionMeEntity {
    private String attentionIds;

    private String userIds;

    private String userNames;

    private Date attentionDate;
}
