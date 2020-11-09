package com.qianbing.blog.component;

import com.qianbing.blog.exception.BizCodeExcetionEnum;
import com.qianbing.blog.utils.R;
import com.qianbing.blog.utils.sms.HttpUtils;
import lombok.Data;
import org.apache.http.HttpResponse;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties(prefix = "spring.sms")
@Data
@Component
public class SmsComponent {

    private String host;

    private String path;

    private String appcode;

    public R send(String phone, String code){
        String host = this.host;
        String path = this.path;
        String method = "GET";
        String appcode = this.appcode;
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("content", "【好服务系统消息】您的验证码是"+code+"。如非本人操作，请忽略本短信");
        querys.put("mobile", phone);
        try {
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(BizCodeExcetionEnum.SMS_SEND_EXCEPTION.getCode(),BizCodeExcetionEnum.SMS_SEND_EXCEPTION.getMsg());
        }
    }
}
