package com.tc51.oacms.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tc51.oacms.system.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {

}
