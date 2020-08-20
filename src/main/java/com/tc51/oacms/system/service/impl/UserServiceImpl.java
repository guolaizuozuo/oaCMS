package com.tc51.oacms.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tc51.oacms.common.bean.Constast;
import com.tc51.oacms.system.domain.Dept;
import com.tc51.oacms.system.domain.User;
import com.tc51.oacms.system.mapper.DeptMapper;
import com.tc51.oacms.system.mapper.RoleMapper;
import com.tc51.oacms.system.mapper.UserMapper;
import com.tc51.oacms.system.service.UserService;
import com.tc51.oacms.system.vo.UserVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;


@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DeptMapper deptMapper;


    @Override
    public boolean removeById(Serializable id) {
        //根据用户ID删除用户角色中间表的数据
        roleMapper.deleteRoleUserByUid(id);
        //删除用户头[如果是默认头像不删除  否则删除]
        return super.removeById(id);
    }

    @Override
    public void saveUserRole(Integer uid, Integer[] ids) {
        //根据用户ID删除sys_role_user里面的数据
        this.roleMapper.deleteRoleUserByUid(uid);
        if (null != ids && ids.length > 0) {
            for (Integer rid : ids) {
                this.roleMapper.insertUserRole(uid, rid);
            }
        }
    }

    @Override
    public IPage loadAllUser(UserVo userVo) {

        IPage<User> page = new Page<>(userVo.getPage(), userVo.getLimit());
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();


        queryWrapper.eq("type", Constast.USER_TYPE_NORMAL);//查询系统用户

        //queryWrapper.like(StringUtils.isNotBlank(userVo.getName()), "loginname", userVo.getName()).or().like(StringUtils.isNotBlank(userVo.getName()), "name", userVo.getName());
        if (StringUtils.isNotBlank(userVo.getName())) {
            queryWrapper.and(wrapper -> wrapper.like("loginname", userVo.getName())
                    .or().like("name", userVo.getName()));
        }

        queryWrapper.eq(userVo.getDeptid() != null, "deptid", userVo.getDeptid());
        this.userMapper.selectPage(page, queryWrapper);

        List<User> list = page.getRecords();
        for (User user : list) {
            Integer deptid = user.getDeptid();
            if (deptid != null) {
                Dept one = deptMapper.selectById(deptid);
                user.setDeptname(one.getTitle());
            }
        }
        page.setRecords(list);
        return page;
    }

    @Override
    public User getById(Serializable id) {
        return super.getById(id);
    }
}
