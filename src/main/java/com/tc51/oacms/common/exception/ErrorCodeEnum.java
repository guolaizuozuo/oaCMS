package com.tc51.oacms.common.exception;


/**
 * 业务错误码：返回结果的状态码
 * <p>
 * 如果想要代码更具维护性一点,可以定义不同种类的错误码,都实现 BaseCodeInterface
 **/

public enum ErrorCodeEnum implements BaseCodeInterface {

    // 数据操作错误定义
    BODY_NOT_MATCH(400, "请求的数据格式不符!"),
    SIGNATURE_NOT_MATCH(401, "请求的数字签名不匹配!"),
    NOT_FOUND(404, "未找到该资源!"),
    INTERNAL_SERVER_ERROR(500, "服务器内部错误!"),
    SERVER_BUSY(503, "服务器正忙，请稍后再试!"),
    //用户相关：10000**
    USER_ACCOUNT_NOT_FOUND(10001, "账号不存在!"),
    DoNotAllowToDisableTheCurrentUser(10002, "不允许禁用当前用户"),
    //业务异常
    PRODUCT_IS_REMOVE(30001, "物资已移入回收站");

    /**
     * 错误码
     */
    private int resultCode;

    /**
     * 错误描述
     */
    private String resultMsg;

    ErrorCodeEnum(int resultCode, String resultMsg) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    @Override
    public int getResultCode() {
        return resultCode;
    }

    @Override
    public String getResultMsg() {
        return resultMsg;
    }}
