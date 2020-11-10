package com.qianbing.blog.controller;

import com.qianbing.blog.component.SmsComponent;
import com.qianbing.blog.constrant.SmsContrant;
import com.qianbing.blog.entity.UsersEntity;
import com.qianbing.blog.exception.BizCodeExcetionEnum;
import com.qianbing.blog.exception.PhoneExistException;
import com.qianbing.blog.service.UsersService;
import com.qianbing.blog.utils.R;
import com.qianbing.blog.vo.LoginVo;
import com.qianbing.blog.vo.RegisterVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/api/v1/pub/")
public class LoginController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private SmsComponent smsComponent;

    @Autowired
    private UsersService usersService;

    @GetMapping("/sms/send/{phone}")
    @ResponseBody
    public R sendCode(@PathVariable("phone") String phone){
        //设置频繁登录限制
        String s = stringRedisTemplate.opsForValue().get(SmsContrant.SMS_CODE + phone);//sms:code:18852734080
        if(!StringUtils.isEmpty(s)){
            String[] sms = s.split("_");
            long time = System.currentTimeMillis();
            if(time - Long.parseLong(sms[1])<60000){
                return R.error(BizCodeExcetionEnum.SMS_SEND_MORE_EXCEPTION.getCode(),BizCodeExcetionEnum.SMS_SEND_MORE_EXCEPTION.getMsg());
            }
        }
        //设置定时时间
        String randomId =  (int)((Math.random()*9+1)*10000)+"";
        String uuid = randomId + "_" + System.currentTimeMillis();
        stringRedisTemplate.opsForValue().set(SmsContrant.SMS_CODE+phone,uuid,300, TimeUnit.SECONDS);
        return smsComponent.send(phone,randomId);
    }

    /**
     * 用户注册
     * @param registerVo
     * @param result
     * @param httpServletRequest
     * @return
     */
    @PostMapping("/user/register")
    @ResponseBody
    public R userRegister(@RequestBody @Validated  RegisterVo registerVo, BindingResult result, HttpServletRequest httpServletRequest){
        //注册之前验证
        String s = stringRedisTemplate.opsForValue().get(SmsContrant.SMS_CODE + registerVo.getUserTelephoneNumber());//sms:code:18852734080
        if(result.hasErrors()){
            for (FieldError fieldError : result.getFieldErrors()) {
                return R.error(fieldError.getDefaultMessage());
            }
        }
        if(StringUtils.isEmpty(registerVo.getCode())){
            return R.error(SmsContrant.CODE_NULL);
        }
        //进行注册
        if(!StringUtils.isEmpty(s)){
            String[] verficaty = s.split("_");
            if(verficaty[0].equals(registerVo.getCode())){
                //删除验证码,令牌
                stringRedisTemplate.delete(SmsContrant.SMS_CODE+registerVo.getUserTelephoneNumber());
                //进行注册
                try {
                    return usersService.register(registerVo,httpServletRequest);
                }catch (Exception e){
                    return R.error(BizCodeExcetionEnum.USER_NAME_EXIST_EXCEPTION.getCode(),BizCodeExcetionEnum.USER_NAME_EXIST_EXCEPTION.getMsg());
                }
            }else{
               return R.error(SmsContrant.CODE_ERROR);
            }
        }else {
            return R.error(SmsContrant.CODE_ERROR);
        }
    }

    /**
     * 用户登录
     * @param vo
     * @param result
     * @return
     */
    @PostMapping("/user/login")
    @ResponseBody
    public R login(@RequestBody @Validated LoginVo vo, BindingResult result){
        if(result.hasErrors()){
            for (FieldError fieldError : result.getFieldErrors()) {
                return R.error(fieldError.getDefaultMessage());
            }
        }
        return usersService.login(vo);
    }
}
