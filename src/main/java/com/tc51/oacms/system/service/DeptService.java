package com.tc51.oacms.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tc51.oacms.system.domain.Dept;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 过来坐坐
 * @since 2020-03-02
 */
public interface DeptService extends IService<Dept> {

    public int loadDeptMaxOrderNum();
    public List<Dept> getDeptList();

}
