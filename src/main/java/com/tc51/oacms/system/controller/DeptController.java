package com.tc51.oacms.system.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.tc51.oacms.common.bean.Constast;
import com.tc51.oacms.common.bean.DataGridView;
import com.tc51.oacms.system.domain.Dept;
import com.tc51.oacms.system.service.DeptService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;


@RestController
@RequestMapping("/admin/dept")
public class DeptController {

    @Autowired
    private DeptService deptService;


    @RequestMapping("loadDept")
    @ResponseBody
    public DataGridView loadDept() {

        List<Dept> deptList = deptService.getDeptList();
        return  new DataGridView(deptList);
    }
}

