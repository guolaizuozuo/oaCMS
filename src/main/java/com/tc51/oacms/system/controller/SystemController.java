package com.tc51.oacms.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("sys")
public class SystemController {

    /**
     * 跳转到首页
     */
    @RequestMapping("index")
    public String index() {
        return "system/index/index";
    }
    /**
     * 跳转到菜单管理
     *
     */
    @RequestMapping("toMenuManager")
    public String toMenuManager() {
        return "system/menu/menuManager";
    }

}
