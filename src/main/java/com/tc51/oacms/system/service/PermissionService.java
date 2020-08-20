package com.tc51.oacms.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tc51.oacms.system.domain.Permission;
import com.tc51.oacms.system.vo.PermissionVo;


public interface PermissionService extends IService<Permission> {

     int loadDeptMaxOrderNum();

     /**
      * 查询当前的ID的菜单有没有子菜单
      */
    boolean checkMenuHasChildrenNode(PermissionVo permissionVo);
}
