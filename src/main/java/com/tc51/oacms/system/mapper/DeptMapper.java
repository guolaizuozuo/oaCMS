package com.tc51.oacms.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tc51.oacms.system.domain.Dept;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 过来坐坐
 * @since 2020-03-02
 */
@Repository
public interface DeptMapper extends BaseMapper<Dept> {


    @Select("SELECT MAX(`ordernum`) FROM `sys_dept` ")
    public int loadDeptMaxOrderNum();

}
