package com.tc51.oacms.common.exception;

import com.alibaba.fastjson.JSON;
import com.tc51.oacms.common.bean.ResponseBean;
import org.apache.shiro.ShiroException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class ExceptionController {



    // 捕捉shiro的异常
    @ExceptionHandler(ShiroException.class)
    public void handle401(HttpServletRequest request,
                          HttpServletResponse response) {
       // return resultMap.fail().code(401).message("您没有权限访问！");
   if(isAjax(request)){
       response.setContentType("application/json; charset=utf-8");//返回json
       try {
           response.getWriter().write(JSON.toJSONString(ResponseBean.PERM_ERROR));
       } catch (IOException e) {
           e.printStackTrace();
       }
   }
   else {
       response.setContentType("text/html; charset=utf-8");//返回json
       PrintWriter out = null;
       try {
           out = response.getWriter();
       } catch (IOException e) {
           e.printStackTrace();
       }
       out.write("没有权限 ");

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
