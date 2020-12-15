package com.qianbing.blog.controller;

import java.util.Arrays;
import java.util.Map;

import com.qianbing.blog.constrant.StayMessageConstrant;
import com.qianbing.blog.entity.StayMessageEntity;
import com.qianbing.blog.service.StayMessageService;
import com.qianbing.blog.utils.PageUtils;
import com.qianbing.blog.utils.R;
import eu.bitwalker.useragentutils.UserAgent;
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
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = stayMessageService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 查看parentstayId是stayId的留言信息
     * @param stayId
     * @return
     */
    @RequestMapping("/info/{stayId}")
    //@RequiresPermissions("coupon:staymessage:info")
    public R info(@PathVariable("stayId") Long stayId){
		StayMessageEntity stayMessage = stayMessageService.selectStayInfo(stayId);
        return R.ok().put("data", stayMessage);
    }

    /**
     * 添加留言
     * @param stayMessage
     * @param request
     * @return
     */
    @RequestMapping("/save")
    public R save(@RequestBody StayMessageEntity stayMessage, HttpServletRequest request){
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        // 获取客户端操作系统
        String sys = userAgent.getOperatingSystem().getName();
        // 获取客户端浏览器
        String chrome = userAgent.getBrowser().getName();
        Integer userId = (Integer) request.getAttribute("id");
        stayMessage.setStayUserId(userId.longValue());
        stayMessage.setStaySys(sys);
        stayMessage.setStayChrome(chrome);
		return stayMessageService.addStayMessage(stayMessage,request);
    }

    /**
     * 修改留言内容
     * @param stayMessage
     * @return
     */
    @RequestMapping("/update")
    public R update(@RequestBody StayMessageEntity stayMessage){
		stayMessageService.updateById(stayMessage);
        return R.ok(StayMessageConstrant.STAY_UPDATE_SUCCESS);
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
