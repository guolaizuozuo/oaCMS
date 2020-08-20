package com.tc51.oacms.system.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.tc51.oacms.common.bean.*;
import com.tc51.oacms.system.domain.Permission;
import com.tc51.oacms.system.domain.User;
import com.tc51.oacms.system.service.PermissionService;
import com.tc51.oacms.system.service.RoleService;
import com.tc51.oacms.system.vo.PermissionVo;
import com.tc51.oacms.utils.WebUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * <p>
 * 菜单管理前端控制器
 * </p>
 */
@Controller
@RequestMapping("/admin/menu")
public class MenuController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;


    @RequestMapping("loadIndexLeftMenuJson")
    @ResponseBody
    public DataGridView loadIndexLeftMenuJson(PermissionVo permissionVo) {
        //查询所有菜单
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        //设置只能查询菜单
        queryWrapper.eq("type", Constast.TYPE_MNEU);
        queryWrapper.eq("available", Constast.AVAILABLE_TRUE);

        User user = (User) WebUtils.getSession().getAttribute("user");
        List<Permission> list = null;
        if (user.getType() == Constast.USER_TYPE_SUPER) {
            list = permissionService.list(queryWrapper);
        } else {
            //根据用户ID+角色+权限去查询
            //获取当前用户ID
            Integer userId = user.getId();
            //获取用户的角色id
            List<Integer> roles = roleService.queryUserRoleIdsByUid(userId);
            //根据角色id获取菜单id  要排重
            Set<Integer> pids = new HashSet<>();
            for (Integer roleid : roles) {
                List<Integer> list1 = roleService.queryRolePermissionIdsByRid(roleid);
                pids.addAll(list1);
            }
            if (pids.size() > 0) {
                queryWrapper.in("id", pids);
                list = permissionService.list(queryWrapper);
            } else {
                list = new ArrayList();
            }


        }

        List<TreeNode> treeNodes = new ArrayList<>();
        for (Permission p : list) {
            Integer id = p.getId();
            Integer pid = p.getPid();
            String title = p.getTitle();
            String icon = p.getIcon();
            String href = p.getHref();
            Boolean spread = p.getOpen() == Constast.OPEN_TRUE ? true : false;
            treeNodes.add(new TreeNode(id, pid, title, icon, href, spread));
        }
        //构造层级关系
        List<TreeNode> list2 = TreeNodeBuilder.build(treeNodes, 1);
        return new DataGridView(list2);
    }


    @RequestMapping("getMenu")
    @ResponseBody
    public DataGridView getMenu(PermissionVo permissionVo) {


        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("available", Constast.AVAILABLE_TRUE);
        queryWrapper.eq("type", Constast.TYPE_MNEU);

        List list = this.permissionService.list(queryWrapper);

        return new DataGridView(list);
    }

    @RequestMapping("getTreeSelectMenu")
    @ResponseBody
    public List<TreeSelect> getTreeSelectMenu(PermissionVo permissionVo) {

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("available", Constast.AVAILABLE_TRUE);
        queryWrapper.eq("type", Constast.TYPE_MNEU);

        List<Permission> list = this.permissionService.list(queryWrapper);

        List<TreeSelect> treeNodes = new ArrayList<>();
        for (Permission p : list) {
            Integer id = p.getId();
            Integer pid = p.getPid();
            String name = p.getTitle();
            Boolean open = true;
            Boolean checked = false;
            treeNodes.add(new TreeSelect(id, pid, name, open, checked));

        }
        //构造层级关系
        List<TreeSelect> list2 = TreeSelectBuilder.build(treeNodes, 0);
        return list2;
    }


    @RequestMapping("delMenu")
    @ResponseBody
    public ResponseBean delMenu(PermissionVo permissionVo) {
        try {
            //查询当前的ID的菜单有没有子菜单
            boolean hasChildrenNode = this.permissionService.checkMenuHasChildrenNode(permissionVo);

            if (hasChildrenNode) {
                return ResponseBean.DELETE_ERROR_Children;
            }
            boolean b = this.permissionService.removeById(permissionVo.getId());
            return ResponseBean.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseBean.DELETE_ERROR;
        }


    }


    @GetMapping("addMenu")
    public String addMenu() {
        return "/system/menu/addMenu";

    }

    @PostMapping("addMenu")
    @ResponseBody
    public ResponseBean addMenuAction(Permission permission) {

        boolean save = this.permissionService.save(permission);
        if (save)
            return ResponseBean.ADD_SUCCESS;
        else
            return ResponseBean.ADD_ERROR;
    }

    @GetMapping("editMenu")
    public String editMenu(int id,Map map) {
        Permission parm = this.permissionService.getById(id);
        map.put("parm",parm);
        return "/system/menu/editMenu";

    }
    @PostMapping("editMenu")
    @ResponseBody
    public ResponseBean editMenuAction(Permission permission) {
        boolean save = this.permissionService.updateById(permission);
        if (save)
            return ResponseBean.UPDATE_SUCCESS;
        else
            return ResponseBean.UPDATE_ERROR;

    }






}

