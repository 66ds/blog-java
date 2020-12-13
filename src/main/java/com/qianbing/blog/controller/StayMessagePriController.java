package com.qianbing.blog.controller;

import java.util.Arrays;
import java.util.Map;

import com.qianbing.blog.entity.StayMessageEntity;
import com.qianbing.blog.service.StayMessageService;
import com.qianbing.blog.utils.PageUtils;
import com.qianbing.blog.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



/**
 * 
 *
 * @author qianbing
 * @email 1532498760@qq.com
 * @date 2020-12-13 20:46:28
 */
@RestController
@RequestMapping("/api/v1/pri/staymessage")
public class StayMessagePriController {
    @Autowired
    private StayMessageService stayMessageService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("coupon:staymessage:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = stayMessageService.queryPage(params);

        return R.ok().put("page", page);
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
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("coupon:staymessage:save")
    public R save(@RequestBody StayMessageEntity stayMessage){
		stayMessageService.save(stayMessage);

        return R.ok();
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
