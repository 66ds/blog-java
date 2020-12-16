package com.qianbing.blog.controller;

import com.qianbing.blog.constrant.FriendLinkConstrant;
import com.qianbing.blog.entity.FriendlyLinkEntity;
import com.qianbing.blog.service.FriendlyLinkService;
import com.qianbing.blog.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * 
 *
 * @author qianbing
 * @email 1532498760@qq.com
 * @date 2020-12-16 20:43:29
 */
@RestController
@RequestMapping("/api/v1/pub/friendlylink")
public class FriendlyLinkPubController {
    @Autowired
    private FriendlyLinkService friendlyLinkService;

    /**
     * 查询所有友情链接
     * @return
     */
    @RequestMapping("/list")
    public R list(){
        List<FriendlyLinkEntity> list = friendlyLinkService.selectList();
        return R.ok().setData(list);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{linkId}")
    //@RequiresPermissions("coupon:friendlylink:info")
    public R info(@PathVariable("linkId") Long linkId){
		FriendlyLinkEntity friendlyLink = friendlyLinkService.getById(linkId);

        return R.ok().put("friendlyLink", friendlyLink);
    }

    /**
     * 添加友链信息
     * @param friendlyLink
     * @return
     */
    @RequestMapping("/save")
    public R save(@RequestBody FriendlyLinkEntity friendlyLink){
        friendlyLink.setLinkCreateTime(new Date());
        friendlyLink.setLinkAllow(0L);
		friendlyLinkService.save(friendlyLink);
        return R.ok(FriendLinkConstrant.LINK_ADD_SUCCESS);
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("coupon:friendlylink:update")
    public R update(@RequestBody FriendlyLinkEntity friendlyLink){
		friendlyLinkService.updateById(friendlyLink);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("coupon:friendlylink:delete")
    public R delete(@RequestBody Long[] linkIds){
		friendlyLinkService.removeByIds(Arrays.asList(linkIds));

        return R.ok();
    }

}
