package com.xingshi.bean;

public class PopRuleBean {

    /**
     * id : 26
     * paramTypeId : 7
     * paramName : 提现手续费（单笔）
     * paramCode : CASH_WITHDRAWAL_SERVICE_CHARGE
     * paramValue : 1.00
     * paramContent : 提现手续费（单笔）
     * createTime : 2019-11-27 10:28:33
     */

    private int id;
    private int paramTypeId;
    private String paramName;
    private String paramCode;
    private String paramValue;
    private String paramContent;
    private String createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParamTypeId() {
        return paramTypeId;
    }

    public void setParamTypeId(int paramTypeId) {
        this.paramTypeId = paramTypeId;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamCode() {
        return paramCode;
    }

    public void setParamCode(String paramCode) {
        this.paramCode = paramCode;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public String getParamContent() {
        return paramContent;
    }

    public void setParamContent(String paramContent) {
        this.paramContent = paramContent;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
