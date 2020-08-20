package com.tc51.oacms.common.bean;


public class ResponseBean {

    public static final ResponseBean  LOGIN_SUCCESS=new ResponseBean(Constast.OK, "登陆成功");
    public static final ResponseBean  LOGIN_ERROR_PASS=new ResponseBean(Constast.ERROR, "登陆失败,用户名或密码不正确");
    public static final ResponseBean  LOGIN_ERROR_CODE=new ResponseBean(Constast.ERROR, "登陆失败,验证码不正确");

    public static final ResponseBean  ADD_SUCCESS=new ResponseBean(Constast.OK, "添加成功");
    public static final ResponseBean  ADD_ERROR=new ResponseBean(Constast.ERROR, "添加失败");

    public static final ResponseBean  UPDATE_SUCCESS=new ResponseBean(Constast.OK, "更新成功");
    public static final ResponseBean  UPDATE_ERROR=new ResponseBean(Constast.ERROR, "更新失败");

    public static final ResponseBean  DELETE_SUCCESS=new ResponseBean(Constast.OK, "删除成功");
    public static final ResponseBean  DELETE_ERROR=new ResponseBean(Constast.ERROR, "删除失败");

    public static final ResponseBean  DELETE_ERROR_Children=new ResponseBean(Constast.ERROR, "此菜单下存在子菜单");

    public static final ResponseBean  TIME_OUT=new ResponseBean(Constast.TIME_OUT, "请求超时,请重新登录");


    public static final ResponseBean  RESET_SUCCESS=new ResponseBean(Constast.OK, "重置成功");
    public static final ResponseBean  RESET_ERROR=new ResponseBean(Constast.ERROR, "重置失败");

    public static final ResponseBean  DISPATCH_SUCCESS=new ResponseBean(Constast.OK, "分配成功");
    public static final ResponseBean  DISPATCH_ERROR=new ResponseBean(Constast.ERROR, "分配失败");

    public static final ResponseBean  PERM_ERROR=new ResponseBean(Constast.ERROR, "没有权限");
    public ResponseBean(){}

    public ResponseBean(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    // http 状态码
    private int code;

    // 返回信息
    private String msg;

    // 返回的数据
    private Object data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
