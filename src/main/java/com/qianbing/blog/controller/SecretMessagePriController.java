package com.qianbing.blog.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.qianbing.blog.entity.SecretMessageEntity;
import com.qianbing.blog.service.SecretMessageService;
import com.qianbing.blog.utils.PageUtils;
import com.qianbing.blog.utils.R;
import com.qianbing.blog.vo.MessageVo;
import com.qianbing.blog.vo.MessagesVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


/**
 * 
 *
 * @author qianbing
 * @email 1532498760@qq.com
 * @date 2020-12-09 14:18:27
 */
@RestController
@RequestMapping("/api/v1/pri/secretmessage")
public class SecretMessagePriController {
    @Autowired
    private SecretMessageService secretMessageService;


    /**
     * 信息
     */
    @RequestMapping("/info/{secretId}")
    //@RequiresPermissions("${moduleName}:secretmessage:info")
    public R info(@PathVariable("secretId") Long secretId){
		SecretMessageEntity secretMessage = secretMessageService.getById(secretId);

        return R.ok().put("secretMessage", secretMessage);
    }

    /**
     * 添加私信
     * @param secretMessage
     * @return
     */
    @RequestMapping("/save")
    public R save(@RequestBody SecretMessageEntity secretMessage,HttpServletRequest request){
        Integer id = (Integer) request.getAttribute("id");
        //判断是不是自己和自己私信，如果是则不增加
        if(secretMessage.getSendId().equals(id.longValue())){
            return R.ok();
        }
        secretMessage.setParentSecretId(0L);
        secretMessage.setIsRead(0L);
        secretMessage.setCreateTime(new Date());
        secretMessage.setReceiveId(id.longValue());
		return secretMessageService.saveSecretMessage(secretMessage);
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("${moduleName}:secretmessage:update")
    public R update(@RequestBody SecretMessageEntity secretMessage){
		secretMessageService.updateById(secretMessage);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("${moduleName}:secretmessage:delete")
    public R delete(@RequestBody Long[] secretIds){
		secretMessageService.removeByIds(Arrays.asList(secretIds));

        return R.ok();
    }

    /**
     * 获取登陆者用户的所有私信用户
     * @param request
     * @return
     */
    @RequestMapping("/users")
    public R  selectMessageList(HttpServletRequest request){
        Integer id = (Integer) request.getAttribute("id");
        List<MessageVo> list = secretMessageService.selectMessageList(id);
        return R.ok().setData(list);
    }

    /**
     * 获取私信内容
     * @param sendId
     * @param request
     * @return
     */
    @RequestMapping("/messages")
    public R selectMessagesList(Integer sendId,HttpServletRequest request){
        Integer id = (Integer) request.getAttribute("id");
        List<MessagesVo> list = secretMessageService.selectMessagesList(sendId,id);
        return R.ok().setData(list);
    }

}
