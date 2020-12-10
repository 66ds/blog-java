package com.qianbing.blog.vo;

import com.qianbing.blog.entity.SecretMessageEntity;
import lombok.Data;

@Data
public class MessagesVo extends SecretMessageEntity {

    private String sendName;

    private String receivedName;

    private String sendImg;

    private String receivedImg;
}
