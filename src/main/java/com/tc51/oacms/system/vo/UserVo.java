package com.tc51.oacms.system.vo;

import com.tc51.oacms.system.domain.User;

public class UserVo extends User {
    private static final long serialVersionUID = 1L;

    private Integer page=1;
    private Integer limit=3;


    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
