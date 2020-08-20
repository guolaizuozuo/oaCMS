package com.tc51.oacms.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tc51.oacms.system.domain.Role;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.io.Serializable;
import java.util.List;


public interface RoleMapper extends BaseMapper<Role> {

    @Select("SELECT pid FROM `sys_role_permission` WHERE rid=#{rid}")
    List<Integer> queryRolePermissionIdsByRid(Integer roleId);

    @Delete("delete from sys_role_user where uid =#{id}")
    void deleteRoleUserByUid(Serializable id);

    @Select("select rid from sys_role_user where uid=#{value}")
    List<Integer> queryUserRoleIdsByUid(Integer id);



    /**
     * 保存角色和用户的关系
     * @param uid
     * @param rid
     */
    @Insert("insert into sys_role_user(uid,rid) values(#{uid},#{rid})")
    void insertUserRole(@Param("uid") Integer uid, @Param("rid") Integer rid);

    @Delete("delete from sys_role_permission where rid=#{value}")
    void deleteRolePermissionByRid(Integer rid);

    @Insert("insert into sys_role_permission(rid,pid) values(#{rid},#{pid})")
    void saveRolePermission(@Param("rid") Integer rid, @Param("pid") Integer pid);
}
