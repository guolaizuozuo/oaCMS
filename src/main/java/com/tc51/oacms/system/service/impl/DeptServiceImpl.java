package com.tc51.oacms.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tc51.oacms.common.bean.Constast;
import com.tc51.oacms.system.domain.Dept;
import com.tc51.oacms.system.mapper.DeptMapper;
import com.tc51.oacms.system.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;


@Service
@Transactional
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements DeptService {

    @Autowired
  private DeptMapper deptMapper;

    public int loadDeptMaxOrderNum(){
        return deptMapper.loadDeptMaxOrderNum();
    }

    @Override
    public List<Dept> getDeptList() {
        QueryWrapper<Dept> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("available", Constast.AVAILABLE_TRUE);

        queryWrapper.orderByAsc("ordernum");
        List<Dept> list = this.deptMapper.selectList(queryWrapper);

        return list;
    }


}
