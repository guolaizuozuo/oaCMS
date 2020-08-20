package com.tc51.oacms.system.service.impl;



import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tc51.oacms.common.bean.ActiverUser;
import com.tc51.oacms.system.domain.Log;
import com.tc51.oacms.system.domain.LoginLog;
import com.tc51.oacms.system.mapper.LogMapper;
import com.tc51.oacms.system.mapper.LoginLogMapper;
import com.tc51.oacms.system.service.LogService;
import com.tc51.oacms.system.service.LoginLogService;
import com.tc51.oacms.utils.AddressUtil;
import com.tc51.oacms.utils.IPUtil;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;


import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLog> implements LoginLogService {



    /**
     * 插入登入日志
     * @param request
     */
    @Transactional
    @Override
    public void add(HttpServletRequest request) {
       this.baseMapper.insert(createLoginLog(request)) ;
    }

    /**
     * 创建登入日志
     * @param
     * @return
     */
    public static LoginLog createLoginLog(HttpServletRequest request) {
        ActiverUser activeUser = (ActiverUser) SecurityUtils.getSubject().getPrincipal();
        LoginLog loginLog = new LoginLog();
        loginLog.setUsername(activeUser.getUser().getName());
        loginLog.setIp(IPUtil.getIpAddr(request));
        loginLog.setLocation(AddressUtil.getCityInfo(IPUtil.getIpAddr(request)));
        // 获取客户端操作系统
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        Browser browser = userAgent.getBrowser();
        OperatingSystem os = userAgent.getOperatingSystem();
        loginLog.setUserSystem(os.getName());
        loginLog.setUserBrowser(browser.getName());
        loginLog.setLoginTime(new Date());
        return loginLog;
    }



}
