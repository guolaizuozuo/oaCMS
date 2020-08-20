package com.tc51.oacms.system.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tc51.oacms.common.annotation.ControllerEndpoint;
import com.tc51.oacms.common.bean.Constast;
import com.tc51.oacms.common.bean.DataGridView;
import com.tc51.oacms.common.bean.ResponseBean;
import com.tc51.oacms.common.bean.TreeNode;
import com.tc51.oacms.system.domain.Permission;
import com.tc51.oacms.system.domain.Role;
import com.tc51.oacms.system.service.PermissionService;
import com.tc51.oacms.system.service.RoleService;
import com.tc51.oacms.system.vo.RoleVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/admin/role")
public class RoleController {

    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;


    @RequestMapping("toRoleManager")
    public String toRoleManager(){

        return "system/role/roleManager";
    }

    /**
     * 查询
     */
    @RequestMapping("loadAllRole")
    @ResponseBody
    public DataGridView loadAllRole(RoleVo roleVo) {
        IPage<Role> page = new Page<>(roleVo.getPage(), roleVo.getLimit());
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(roleVo.getName()), "name", roleVo.getName());
        queryWrapper.like(StringUtils.isNotBlank(roleVo.getRemark()), "remark", roleVo.getRemark());
        queryWrapper.eq(roleVo.getAvailable() != null, "available", roleVo.getAvailable());
        queryWrapper.orderByDesc("createtime");
        this.roleService.page(page, queryWrapper);
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    @RequestMapping("selectRole")
    public String selectRole(Integer id,Map map){
        map.put("id",id);
        return "system/role/selectRole";
    }

    /**
     * 根据用户ID查询角色并选中已拥有的角色
     */
    @RequestMapping("initRoleByUserId")
    @ResponseBody
    public DataGridView initRoleByUserId(Integer id) {

        List<Map<String, Object>> maps = this.roleService.getRoleByUserId(id);
        return new DataGridView(Long.valueOf(maps.size()), maps);

    }


    @RequestMapping("selectPermPage")
    public String assignmentPermissionByRoleId(Integer rid,Map map){
        map.put("rid",rid);
        return "system/role/selectPerm";
    }

    /**
     * 根据角色ID加载菜单和权限的树的json串
     */
    @RequestMapping("initPermissionByRoleId")
    @ResponseBody
    public DataGridView initPermissionByRoleId(Integer roleId) {
        //查询所有可用的菜单和权限
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("available", Constast.AVAILABLE_TRUE);
        List<Permission> allPermissions = permissionService.list(queryWrapper);

        /**
         * 1,根据角色ID查询当前角色拥有的所有的权限或菜单ID
         * 2,根据查询出来的菜单ID查询权限和菜单数据
         */
        List<Integer> currentRolePermissions = this.roleService.queryRolePermissionIdsByRid(roleId);

        //构造 List<TreeNode>
        List<TreeNode> nodes = new ArrayList<>();
        for (Permission p1 : allPermissions) {
            Boolean spread = (p1.getOpen() == null || p1.getOpen() == 1) ? true : false;
            String checkArr = "0";
            for (Integer p2 : currentRolePermissions) {
                if (p2.intValue() == p1.getId()) {
                    checkArr = "1";
                }
            }
            nodes.add(new TreeNode(p1.getId(), p1.getPid(), p1.getTitle(), spread, checkArr));
        }

        return new DataGridView(nodes);
    }
    /**
     * 保存角色和菜单权限之间的关系
     */
    @ControllerEndpoint(exceptionMessage = "分配权限失败", operation = "分配权限")
    @RequestMapping("saveRolePermission")
    @ResponseBody
    public ResponseBean saveRolePermission(Integer rid, Integer[] ids) {
        try {
            this.roleService.saveRolePermission(rid,ids);
            return ResponseBean.DISPATCH_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseBean.DISPATCH_ERROR;
        }
    }

}

