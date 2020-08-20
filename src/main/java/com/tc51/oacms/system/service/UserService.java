package com.tc51.oacms.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tc51.oacms.system.domain.User;
import com.tc51.oacms.system.vo.UserVo;

public interface UserService extends IService<User> {


    void saveUserRole(Integer uid, Integer[] ids);

    IPage loadAllUser(UserVo userVo);
}
