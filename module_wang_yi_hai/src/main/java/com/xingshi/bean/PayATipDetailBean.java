package com.xingshi.bean;

import java.io.Serializable;

public class PayATipDetailBean implements Serializable {

    /**
     * id : 4
     * userCode : 329064267913363456
     * userName : 18639488962
     * createTime : 2019-12-24 11:19:07
     * endTime : 2019-12-30 12:00:00
     * ordinaryPrice : 1.0
     * shareholderPrice : 1.0
     * title : 哈哈
     * pictureUrl : null
     * content : 哈哈哈
     * icon : null
     */

    private int id;
    private String userCode;
    private String userName;
    private String createTime;
    private String endTime;
    private double ordinaryPrice;
    private double shareholderPrice;
    private String title;
    private String pictureUrl;
    private String content;
    private String icon;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public double getOrdinaryPrice() {
        return ordinaryPrice;
    }

    public void setOrdinaryPrice(double ordinaryPrice) {
        this.ordinaryPrice = ordinaryPrice;
    }

    public double getShareholderPrice() {
        return shareholderPrice;
    }

    public void setShareholderPrice(double shareholderPrice) {
        this.shareholderPrice = shareholderPrice;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
