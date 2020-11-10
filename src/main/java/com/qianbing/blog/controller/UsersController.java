package com.qianbing.blog.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.qianbing.blog.entity.UsersEntity;
import com.qianbing.blog.service.UsersService;
import com.qianbing.blog.utils.PageUtils;
import com.qianbing.blog.utils.R;



/**
 * 
 *
 * @author qianbing
 * @email 1532498760@qq.com
 * @date 2020-11-09 09:12:57
 */
@RestController
@RequestMapping("/api/v1/pri/users")
public class UsersController {
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
     * 信息
     */
    @RequestMapping("/info/{userId}")
    //@RequiresPermissions("admin:users:info")
    public R info(@PathVariable("userId") Long userId){
		UsersEntity users = usersService.getById(userId);

        return R.ok().put("users", users);
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
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("admin:users:update")
    public R update(@RequestBody UsersEntity users){
		usersService.updateById(users);

        return R.ok();
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

}
