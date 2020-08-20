package com.tc51.oacms.shiro.filter;


import com.alibaba.fastjson.JSON;
import com.tc51.oacms.common.bean.ResponseBean;
import org.apache.shiro.web.filter.authc.UserFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;

public class ShiroLoginFilter extends UserFilter {
    @Override
    protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
        // super.redirectToLogin(request, response);
        HttpServletRequest hq = (HttpServletRequest) request;
        if (this.isAjax(hq)) {
            response.setContentType("application/json; charset=utf-8");//返回json
             response.getWriter().write(JSON.toJSONString(ResponseBean.TIME_OUT));

        } else {
            response.setContentType("text/html; charset=utf-8");//返回json
            PrintWriter out = response.getWriter();
            out.write("<script> alert('系统超时，请重新登录');top.location.href = '/login'; </script>");
        }
    }


    /**
     * 判断ajax请求
     *
     * @param request
     * @return
     */
    private boolean isAjax(HttpServletRequest request) {
        return (request.getHeader("X-Requested-With") != null
                && "XMLHttpRequest".equals(request.getHeader("X-Requested-With").toString()));
    }
}
