package com.qianbing.blog.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.qianbing.blog.entity.SecretMessageEntity;
import com.qianbing.blog.service.SecretMessageService;
import com.qianbing.blog.utils.PageUtils;
import com.qianbing.blog.utils.R;
import com.qianbing.blog.vo.MessageVo;
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
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("${moduleName}:secretmessage:save")
    public R save(@RequestBody SecretMessageEntity secretMessage){
		secretMessageService.save(secretMessage);

        return R.ok();
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
     * 获取登陆者用户的所有私信
     * @param request
     * @return
     */
    @RequestMapping("/list")
    public R  selectMessageList(HttpServletRequest request){
        Integer id = (Integer) request.getAttribute("id");
        List<MessageVo> list = secretMessageService.selectMessageList(id);
        return R.ok().setData(list);
    }

}
