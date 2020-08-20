package com.tc51.oacms.system.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tc51.oacms.common.bean.ResponseBean;
import com.tc51.oacms.system.domain.Log;
import com.tc51.oacms.system.vo.LogVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统日志
 */
public interface LogService {

    /**
     * 异步保存操作日志
     */
    @Async("CodeAsyncThreadPool")
    void saveLog(Log log);


    IPage loadAllUser(LogVo logVo);
    void batchDelete(List<Long> list);

}
