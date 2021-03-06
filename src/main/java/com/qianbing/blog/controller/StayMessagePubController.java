package com.qianbing.blog.controller;

import com.qianbing.blog.entity.StayMessageEntity;
import com.qianbing.blog.service.StayMessageService;
import com.qianbing.blog.utils.PageUtils;
import com.qianbing.blog.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Map;


/**
 * 
 *
 * @author qianbing
 * @email 1532498760@qq.com
 * @date 2020-12-13 20:46:28
 */
@RestController
@RequestMapping("/api/v1/pub/staymessage")
public class StayMessagePubController {
    @Autowired
    private StayMessageService stayMessageService;

    /**
     * 获取所有留言列表
     * @param params
     * @return
     */
    @RequestMapping("/list")
    public R list(@RequestBody Map<String, Object> params){
        PageUtils page = stayMessageService.queryPage(params);
        return R.ok().put("data", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{stayId}")
    //@RequiresPermissions("coupon:staymessage:info")
    public R info(@PathVariable("stayId") Long stayId){
		StayMessageEntity stayMessage = stayMessageService.getById(stayId);

        return R.ok().put("stayMessage", stayMessage);
    }

    /**
     * 添加留言
     * @param stayMessage
     * @param request
     * @return
     */
    @RequestMapping("/save")
    public R save(@RequestBody StayMessageEntity stayMessage, HttpServletRequest request){
        Integer userId = (Integer) request.getAttribute("id");
        stayMessage.setStayUserId(userId.longValue());
		return stayMessageService.addStayMessage(stayMessage,request);
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("coupon:staymessage:update")
    public R update(@RequestBody StayMessageEntity stayMessage){
		stayMessageService.updateById(stayMessage);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("coupon:staymessage:delete")
    public R delete(@RequestBody Long[] stayIds){
		stayMessageService.removeByIds(Arrays.asList(stayIds));

        return R.ok();
    }

}
