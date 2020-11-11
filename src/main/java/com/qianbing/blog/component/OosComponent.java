package com.qianbing.blog.component;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "spring.oos")
@Component
@Data
public class OosComponent{

    private  String endPoint;

    private  String accessKey;

    private  String secretKey;

    private  String bucket;


}
