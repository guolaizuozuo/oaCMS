package com.tc51.oacms.system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tc51.oacms.system.domain.Log;
import com.tc51.oacms.system.domain.Role;
import com.tc51.oacms.system.mapper.LogMapper;
import com.tc51.oacms.system.mapper.RoleMapper;
import com.tc51.oacms.system.service.LogService;
import com.tc51.oacms.system.service.RoleService;
import com.tc51.oacms.system.vo.LogVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;


import java.util.ArrayList;
import java.util.List;


@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements LogService {


    /**
     * 保存登入日志
     *
     * @param log
     */
    @Override
    public void saveLog(Log log) {
        this.baseMapper.insert(log);

    }

    @Override
    public IPage loadAllUser(LogVo logVo) {
        IPage<Log> iPage = new Page<Log>(logVo.getPage(), logVo.getLimit());

        QueryWrapper qw = new QueryWrapper();
        if (StringUtils.isNotBlank(logVo.getUsername())) {
            qw.eq("username",logVo.getUsername());
        }
        if (StringUtils.isNotBlank(logVo.getOperation())) {
            qw.eq("operation",logVo.getOperation());
        }

        qw.eq(StringUtils.isNotBlank(logVo.getIp()),"ip", logVo.getIp());
        qw.ge(logVo.getStartTime()!=null, "create_time", logVo.getStartTime());
        qw.le(logVo.getEndTime()!=null, "create_time", logVo.getEndTime());

        qw.orderByDesc("create_time");
        this.baseMapper.selectPage(iPage, qw);
        return iPage;
    }

    @Override
    public void batchDelete(List<Long> list) {

    }


}