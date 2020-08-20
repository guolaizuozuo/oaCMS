package com.tc51.oacms.system.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.tc51.oacms.system.domain.Role;

import java.util.List;
import java.util.Map;


public interface RoleService extends IService<Role> {

    /**
     * 根据角色ID查询当前角色拥有的所有的权限或菜单ID
     * @param roleId
     * @return
     */
    List<Integer> queryRolePermissionIdsByRid(Integer roleId);

    List<Integer> queryUserRoleIdsByUid(Integer id);

    List<Map<String, Object>>   getRoleByUserId(Integer id);

    void saveRolePermission(Integer rid, Integer[] ids);
}
