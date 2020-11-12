package com.qianbing.blog.controller;

import java.util.Arrays;
import java.util.Map;

import com.qianbing.blog.entity.SetArtitleLabelEntity;
import com.qianbing.blog.service.SetArtitleLabelService;
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
 * @date 2020-11-12 16:13:08
 */
@RestController
@RequestMapping("/api/v1/pri/setartitlelabel")
public class SetArtitleLabelController {
    @Autowired
    private SetArtitleLabelService setArtitleLabelService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("${moduleName}:setartitlelabel:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = setArtitleLabelService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{articleId}")
    //@RequiresPermissions("${moduleName}:setartitlelabel:info")
    public R info(@PathVariable("articleId") Long articleId){
		SetArtitleLabelEntity setArtitleLabel = setArtitleLabelService.getById(articleId);

        return R.ok().put("setArtitleLabel", setArtitleLabel);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("${moduleName}:setartitlelabel:save")
    public R save(@RequestBody SetArtitleLabelEntity setArtitleLabel){
		setArtitleLabelService.save(setArtitleLabel);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("${moduleName}:setartitlelabel:update")
    public R update(@RequestBody SetArtitleLabelEntity setArtitleLabel){
		setArtitleLabelService.updateById(setArtitleLabel);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("${moduleName}:setartitlelabel:delete")
    public R delete(@RequestBody Long[] articleIds){
		setArtitleLabelService.removeByIds(Arrays.asList(articleIds));

        return R.ok();
    }

}
