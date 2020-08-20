package com.tc51.oacms.system.controller;


import com.tc51.oacms.common.bean.ActiverUser;
import com.tc51.oacms.common.bean.ResponseBean;
import com.tc51.oacms.system.service.LoginLogService;
import com.tc51.oacms.utils.WebUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.aspectj.apache.bcel.generic.RET;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class LoginController {

    @Autowired
    private LoginLogService loginLogService;
    /**
     * 用户登入
     *
     * @param username: 用户名
     * @param password: 密码
     * @return
     */
    @PostMapping("/loginAction")
    @ResponseBody
    public ResponseBean login(String username,
                              String password,
                              String code,
                              HttpSession session,
                              HttpServletRequest request) {

        //比较验证码
        String codes = (String) session.getAttribute("code");

        //获取主体对象
        if (codes.equalsIgnoreCase(code)){
            Subject subject = SecurityUtils.getSubject();
            AuthenticationToken token=new UsernamePasswordToken(username, password);
            try {
                subject.login(token);
                ActiverUser activerUser=(ActiverUser) subject.getPrincipal();
                WebUtils.getSession().setAttribute("user", activerUser.getUser());
                loginLogService.add(request);
                return ResponseBean.LOGIN_SUCCESS;
            } catch (AuthenticationException e) {
                e.printStackTrace();
                return ResponseBean.LOGIN_ERROR_PASS;
            }
        }else{
            System.out.println("验证码错误!");
           return ResponseBean.LOGIN_ERROR_CODE;
        }


    }

    @GetMapping("/login")
    public String loginView() {

        return "system/login/index";
    }

}
