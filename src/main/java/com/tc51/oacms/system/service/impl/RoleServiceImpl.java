package com.tc51.oacms.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tc51.oacms.common.bean.Constast;
import com.tc51.oacms.system.domain.Role;
import com.tc51.oacms.system.mapper.RoleMapper;
import com.tc51.oacms.system.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
@Transactional
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    /**
     * 根据角色ID查询当前角色拥有的所有的权限或菜单ID
     */
    @Override
    public List<Integer> queryRolePermissionIdsByRid(Integer roleId) {
        return this.getBaseMapper().queryRolePermissionIdsByRid(roleId);
    }


    /**
     * 查询当前用户拥有的角色ID集合
     */
    @Override
    public List<Integer> queryUserRoleIdsByUid(Integer id) {

        return this.getBaseMapper().queryUserRoleIdsByUid(id);
    }

    @Override
    public List<Map<String, Object>> getRoleByUserId(Integer id) {
        //1,查询所有可用的角色
        QueryWrapper<Role> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("available", Constast.AVAILABLE_TRUE);
        List<Map<String, Object>> listMaps = this.getBaseMapper().selectMaps(queryWrapper);

        //2,查询当前用户拥有的角色ID集合
        List<Integer> currentUserRoleIds=this.queryUserRoleIdsByUid(id);
        for (Map<String, Object> map : listMaps) {
            Boolean LAY_CHECKED=false;
            Integer roleId=(Integer) map.get("id");
            for (Integer rid : currentUserRoleIds) {
                if(rid==roleId) {
                    LAY_CHECKED=true;
                    break;
                }
            }
            map.put("LAY_CHECKED", LAY_CHECKED);
        }
        return listMaps;
    }

    @Override
    public void saveRolePermission(Integer rid, Integer[] ids) {
        RoleMapper roleMapper = this.getBaseMapper();
        //根据rid删除sys_role_permission
        roleMapper.deleteRolePermissionByRid(rid);
        if(ids!=null&&ids.length>0) {
            for (Integer pid : ids) {
                roleMapper.saveRolePermission(rid,pid);
            }
        }
    }


}
