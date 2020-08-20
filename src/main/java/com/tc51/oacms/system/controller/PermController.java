package com.tc51.oacms.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tc51.oacms.common.bean.Constast;
import com.tc51.oacms.common.bean.DataGridView;
import com.tc51.oacms.common.bean.ResponseBean;
import com.tc51.oacms.system.domain.Permission;
import com.tc51.oacms.system.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/perm")
public class PermController {
    @Autowired
    private PermissionService permissionService;


    @GetMapping("toPermissionManager")
    public String toPermissionManager() {
        return "system/perm/permissionManager";
    }
    @GetMapping("addPerm")
    public String addPerm() {
        return "system/perm/addPerm";
    }


    @RequestMapping("getPermList")
    @ResponseBody
    public DataGridView getPermList() {

        QueryWrapper queryWrapper = new QueryWrapper();
        List list = this.permissionService.list(queryWrapper);
        return new DataGridView(list);

    }

    @PostMapping("addPerm")
    @ResponseBody
    public ResponseBean addPermAction(Permission permission) {

        boolean save = this.permissionService.save(permission);
        if (save)
            return ResponseBean.ADD_SUCCESS;
        else
            return ResponseBean.ADD_ERROR;
    }


    @GetMapping("editPerm")
    public String editPerm(int id, Map map) {
        Permission parm = this.permissionService.getById(id);
        map.put("parm",parm);
        return "system/perm/editPerm";
    }

    @PostMapping("editPerm")
    @ResponseBody
    public ResponseBean editPermAction(Permission permission) {
        boolean save = this.permissionService.updateById(permission);
        if (save)
            return ResponseBean.UPDATE_SUCCESS;
        else
            return ResponseBean.UPDATE_ERROR;
    }



}
