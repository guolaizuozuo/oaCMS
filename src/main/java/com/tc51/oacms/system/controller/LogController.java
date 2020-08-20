package com.tc51.oacms.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tc51.oacms.common.bean.DataGridView;
import com.tc51.oacms.common.bean.ResponseBean;
import com.tc51.oacms.system.service.LogService;
import com.tc51.oacms.system.vo.LogVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/admin/log")
public class LogController {

    @Autowired
    private LogService logService;

    @RequestMapping("toLogManager")
    public String toLogManager() {
        return "system/log/toLogManager";
    }

    /**
     * 用户全查询
     */
    @RequestMapping("loadAllLog")
    @ResponseBody
    public DataGridView loadAllUser(LogVo logVo)
    {
        IPage iPage = logService.loadAllUser(logVo);

        return new DataGridView(iPage.getTotal(), iPage.getRecords());
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @RequiresPermissions({"log:batchDelete"})
    @PostMapping("batchDelete")
    //@DeleteMapping("/batchDelete/{ids}")
    public ResponseBean batchDelete(Long[] ids) {

        try {
            List<Long> list2 = Arrays.asList(ids);
            logService.batchDelete(list2);
            return ResponseBean.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseBean.DELETE_ERROR;
        }
    }
}
