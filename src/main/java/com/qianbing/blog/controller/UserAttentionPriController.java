package com.qianbing.blog.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.qianbing.blog.constrant.UserAttentionConstrant;
import com.qianbing.blog.entity.UserAttentionEntity;
import com.qianbing.blog.service.UserAttentionService;
import com.qianbing.blog.utils.PageUtils;
import com.qianbing.blog.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


/**
 * 
 *
 * @author qianbing
 * @email 1532498760@qq.com
 * @date 2020-11-27 22:57:08
 */
@RestController
@RequestMapping("/api/v1/pri/userattention")
public class UserAttentionPriController {
    @Autowired
    private UserAttentionService userAttentionService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("coupon:userattention:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = userAttentionService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 查看用户是不是关注了某人
     * @param userAttention
     * @param request
     * @return
     */
    @RequestMapping("/info")
    public R info(@RequestBody UserAttentionEntity userAttention, HttpServletRequest request){
        Integer id = (Integer) request.getAttribute("id");
        userAttention.setUserId(id.longValue());
		UserAttentionEntity userAttentionEntity = userAttentionService.selectAttentionInfo(userAttention);
        return R.ok().put("data", userAttentionEntity);
    }

    /**
     * 添加用户的关注信息
     * @param attentionId
     * @return
     */
    @RequestMapping("/save/{attentionId}")
    public R save(@PathVariable("attentionId") Long attentionId, HttpServletRequest request){
        Integer id = (Integer) request.getAttribute("id");
        UserAttentionEntity userAttention = new UserAttentionEntity();
        userAttention.setUserId(id.longValue());
        userAttention.setAttentionId(attentionId);
        userAttention.setAttentionDate(new Date());
        //默认未读
        userAttention.setIsRead(0L);
		return userAttentionService.saveAttentionInfo(userAttention);
    }

    /**
     * 获取谁关注我信息
     * @param request
     * @return
     */
    @RequestMapping("/get/who/attention")
    public R getWhoAttentionMeInfo(HttpServletRequest request){
        Integer id = (Integer) request.getAttribute("id");
        return userAttentionService.getWhoAttentionMeInfo(id.longValue());
    }

    /**
     * 清空信息(单个和多个)
     * @param attentionIds
     * @return
     */
    @RequestMapping("/delete/who/attention")
    public R deleteWhoAttentionMeInfo(@RequestBody List<Long> attentionIds){
        return userAttentionService.deleteWhoAttentionMeInfo(attentionIds);
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("coupon:userattention:update")
    public R update(@RequestBody UserAttentionEntity userAttention, HttpServletRequest request){
        Integer id = (Integer) request.getAttribute("id");
        userAttention.setUserId(id.longValue());
		userAttentionService.updateById(userAttention);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("coupon:userattention:delete")
    public R delete(@RequestBody Long[] aIds){
		userAttentionService.removeByIds(Arrays.asList(aIds));
        return R.ok();
    }

}
