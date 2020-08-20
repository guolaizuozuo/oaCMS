package com.tc51.oacms.system.service;



import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


public interface LoginLogService {

    /**
     * 添加登入日志
     * @param request
     */
    void add(HttpServletRequest request);



}
