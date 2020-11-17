package com.qianbing.blog.controller;

import com.qianbing.blog.entity.LabelsEntity;
import com.qianbing.blog.service.LabelsService;
import com.qianbing.blog.utils.PageUtils;
import com.qianbing.blog.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author qianbing
 * @email 1532498760@qq.com
 * @date 2020-11-10 15:44:24
 */
@RestController
@RequestMapping("/api/v1/pub/labels")
public class LabelsPubController {

    @Autowired
    private LabelsService labelsService;
    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(){
        List<LabelsEntity> list = labelsService.list();
        return R.ok().put("data", list);
    }

}
