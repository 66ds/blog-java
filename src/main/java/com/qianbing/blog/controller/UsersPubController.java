package com.qianbing.blog.controller;

import com.qianbing.blog.entity.UsersEntity;
import com.qianbing.blog.service.UsersService;
import com.qianbing.blog.utils.PageUtils;
import com.qianbing.blog.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 
 *
 * @author qianbing
 * @email 1532498760@qq.com
 * @date 2020-11-09 09:12:57
 */
@RestController
@RequestMapping("/api/v1/pub/users")
public class UsersPubController {
    @Autowired
    private UsersService usersService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("admin:users:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = usersService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 获取用户的个人信息
     * @param userId
     * @return
     */
    @RequestMapping("/info/{userId}")
    //@RequiresPermissions("admin:users:info")
    public R info(@PathVariable("userId") Long userId){
		UsersEntity users = usersService.getById(userId);
        return R.ok().put("data", users);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("admin:users:save")
    public R save(@RequestBody UsersEntity users){
		usersService.save(users);
        return R.ok();
    }

    /**
     * 修改个人用户信息
     * @param users
     * @return
     */
    @RequestMapping("/update")
    public R update(@RequestBody UsersEntity users){
        usersService.updateById(users);
        return R.ok().put("data",users);
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("admin:users:delete")
    public R delete(@RequestBody Long[] userIds){
		usersService.removeByIds(Arrays.asList(userIds));

        return R.ok();
    }

    /**
     * 获取某用户的名片信息
     * @param userId
     * @return
     */
    @RequestMapping("/card/{userId}")
    public R selectCardInfo(@PathVariable("userId") Long userId){
        return usersService.selectCardInfo(userId);
    }

}
