package com.tc51.oacms.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tc51.oacms.system.domain.Permission;
import com.tc51.oacms.system.mapper.PermissionMapper;
import com.tc51.oacms.system.service.PermissionService;
import com.tc51.oacms.system.vo.PermissionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public int loadDeptMaxOrderNum() {
        return permissionMapper.loadDeptMaxOrderNum();
    }

    @Override
    public boolean checkMenuHasChildrenNode(PermissionVo permissionVo) {

        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pid", permissionVo.getId());
        List<Permission> list = this.permissionMapper.selectList(queryWrapper);
        if (list.size() > 0) {
            return  true;
        } else {
            return  false;
        }
    }
}
