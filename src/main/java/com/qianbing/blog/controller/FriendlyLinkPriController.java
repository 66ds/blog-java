package com.qianbing.blog.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.qianbing.blog.constrant.FriendLinkConstrant;
import com.qianbing.blog.entity.FriendlyLinkEntity;
import com.qianbing.blog.service.FriendlyLinkService;
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
 * @date 2020-12-16 20:43:29
 */
@RestController
@RequestMapping("/api/v1/pri/friendlylink")
public class FriendlyLinkPriController {
    @Autowired
    private FriendlyLinkService friendlyLinkService;

    /**
     * 查询所有的友情链接(包括通过和未通过)
     * @param params
     * @return
     */
    @RequestMapping("/lists")
    public R list(@RequestBody Map<String,Object> params){
        PageUtils pageUtils = friendlyLinkService.queryPage(params);
        return R.ok().setData(pageUtils);
    }

    /**
     * 查询单个友链信息
     * @param linkId
     * @return
     */
    @RequestMapping("/info/{linkId}")
    public R info(@PathVariable("linkId") Long linkId){
		FriendlyLinkEntity friendlyLink = friendlyLinkService.getById(linkId);
        return R.ok().setData(friendlyLink);
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
     * 修改留言信息
     * @param friendlyLink
     * @return
     */
    @RequestMapping("/update")
    public R update(@RequestBody FriendlyLinkEntity friendlyLink){
       return friendlyLinkService.updateLinkInfo(friendlyLink);
    }

    /**
     * 单个友链删除
     * @param linkId
     * @return
     */
    @RequestMapping("/delete/{linkId}")
    public R delete(@PathVariable("linkId") Long linkId){
        boolean b = friendlyLinkService.removeById(linkId);
        if(!b){
            return R.error(FriendLinkConstrant.LINK_SERVER_ERROR);
        }
        return R.ok(FriendLinkConstrant.LINK_DELETE_SUCCESS);
    }

    /**
     * 批量删除友链
     * @param linkIds
     * @return
     */
    @RequestMapping("/batch")
    public R deleteBatch(@RequestParam(value = "linkIds") @RequestBody Long[] linkIds){
        boolean b = friendlyLinkService.removeByIds(Arrays.asList(linkIds));
        if(!b){
            return R.error(FriendLinkConstrant.LINK_SERVER_ERROR);
        }
        return R.ok(FriendLinkConstrant.LINK_DELETE_SUCCESS);
    }

}
