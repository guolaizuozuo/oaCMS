package com.tc51.oacms.system.controller;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tc51.oacms.common.annotation.ControllerEndpoint;
import com.tc51.oacms.common.bean.Constast;
import com.tc51.oacms.common.bean.DataGridView;
import com.tc51.oacms.common.bean.ResponseBean;
import com.tc51.oacms.system.domain.User;
import com.tc51.oacms.system.service.UserService;
import com.tc51.oacms.system.vo.UserVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping("/admin/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Value("${shiro.hash-iterations}")
    private  int hashIterations;

    @RequestMapping("toUserManager")
    public String toUserManager(){
        return "system/user/userManager";
    }

    /**
     * 用户全查询
     */
    @RequestMapping("loadAllUser")
    @ResponseBody
    public DataGridView loadAllUser(UserVo userVo)
    {
        IPage iPage = userService.loadAllUser(userVo);
        return new DataGridView(iPage.getTotal(), iPage.getRecords());
    }

    @RequestMapping("userAdd")
    public String userAdd(){
        return "system/user/userAdd";
    }
    @RequestMapping("userEdit")
    public String userEdit(int id, Map map){
        User user = this.userService.getById(id);
        map.put("item",user);
        return "system/user/userEdit";
    }

    @RequiresPermissions({"log:batchDelete"})
    @ControllerEndpoint(exceptionMessage = "更新用户失败", operation = "更新用户")
    @PostMapping("userEdit")
    @ResponseBody
    public ResponseBean userEdit(UserVo vo){
        try {
            this.userService.updateById(vo);
            return ResponseBean.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseBean.UPDATE_ERROR;
        }
    }


    @PostMapping("userAdd")
    @ResponseBody
    public ResponseBean userAddAction(UserVo userVo){
        System.out.println("hashIterations:"+hashIterations);
        try {
            userVo.setType(Constast.USER_TYPE_NORMAL);//设置类型
            userVo.setHiredate(new Date());
            String salt= IdUtil.simpleUUID().toUpperCase();
            userVo.setSalt(salt);//设置盐
            userVo.setPwd(new Md5Hash(Constast.USER_DEFAULT_PWD, salt, hashIterations).toString());//设置密码
            this.userService.save(userVo);
            return ResponseBean.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseBean.ADD_ERROR;
        }
    }

    /**
     * 重置用户密码
     */
    @RequestMapping("resetPwd")
    @ResponseBody
    public ResponseBean resetPwd(Integer id){
        try {
            User user=new User();
            user.setId(id);
            String salt=IdUtil.simpleUUID().toUpperCase();
            user.setSalt(salt);//设置盐
            user.setPwd(new Md5Hash(Constast.USER_DEFAULT_PWD, salt, hashIterations).toString());//设置密码
            this.userService.updateById(user);
            return ResponseBean.RESET_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseBean.RESET_ERROR;
        }
    }

    @RequestMapping("saveUserRole")
    @ResponseBody
    public ResponseBean saveUserRole(Integer uid,Integer[] ids){
        try {
            this.userService.saveUserRole(uid,ids);
            return ResponseBean.DISPATCH_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseBean.DISPATCH_ERROR;
        }
    }
}
