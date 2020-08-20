package com.tc51.oacms.common.bean;

import java.util.ArrayList;
import java.util.List;

public class TreeSelect {

    private  Integer id;
    private Integer pid;
    private  String name;
    private  boolean open;
    private  boolean checked;
    private List<TreeSelect> children;

    public TreeSelect(Integer id, Integer pid, String name, boolean open, boolean checked) {
        this.id = id;
        this.pid = pid;
        this.name = name;
        this.open = open;
        this.checked = checked;
    }

    public Integer getId() {
        return id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public List<TreeSelect> getChildren() {
        return children;
    }

    public void setChildren(List<TreeSelect> children) {
        this.children = children;
    }
}
