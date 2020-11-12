package com.qianbing.blog.controller;
import java.util.Arrays;
import java.util.Map;

import com.qianbing.blog.entity.LabelsEntity;
import com.qianbing.blog.service.LabelsService;
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
 * @date 2020-11-12 16:12:50
 */
@RestController
@RequestMapping("/api/v1/pri/labels")
public class LabelsController {
    @Autowired
    private LabelsService labelsService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("${moduleName}:labels:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = labelsService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{labelId}")
    //@RequiresPermissions("${moduleName}:labels:info")
    public R info(@PathVariable("labelId") Long labelId){
		LabelsEntity labels = labelsService.getById(labelId);

        return R.ok().put("labels", labels);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("${moduleName}:labels:save")
    public R save(@RequestBody LabelsEntity labels){
		labelsService.save(labels);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("${moduleName}:labels:update")
    public R update(@RequestBody LabelsEntity labels){
		labelsService.updateById(labels);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("${moduleName}:labels:delete")
    public R delete(@RequestBody Long[] labelIds){
		labelsService.removeByIds(Arrays.asList(labelIds));

        return R.ok();
    }

}
