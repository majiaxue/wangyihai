package com.xingshi.bean;

import com.stx.xhb.xbanner.entity.SimpleBannerInfo;

import java.io.Serializable;

public class RedPackageBean extends SimpleBannerInfo implements Serializable {

    /**
     * id : 13
     * money : 20
     * buyMoney : 10
     * count : 4
     * type : 0
     * useCondition : 20
     * validTime : 1
     * status : 1
     * createTime : 2019-08-27 17:21:48
     * updateTime : 2019-08-27 17:30:01
     * tenantId : 1
     * pic : http://192.168.0.17:9000/goods/7b2e5803b2cf4a189ac6fe6f78f7dc5f.png
     * name : 优惠红包满20减5元
     * note : 111111111111111111111111111111
     */

    private String id;
    private String money;
    private String buyMoney;
    private String count;
    private String type;
    private String useCondition;
    private String validTime;
    private String status;
    private String createTime;
    private String updateTime;
    private String tenantId;
    private String pic;
    private String name;
    private String note;
    private int background;
    private String userCode;
    private String redPackedId;
    private String payTime;
    private String endTime;
    private String payWay;
    private String payMoney;
    private String tradeNo;
    private String sellerId;

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getRedPackedId() {
        return redPackedId;
    }

    public void setRedPackedId(String redPackedId) {
        this.redPackedId = redPackedId;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    public String getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(String payMoney) {
        this.payMoney = payMoney;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getBuyMoney() {
        return buyMoney;
    }

    public void setBuyMoney(String buyMoney) {
        this.buyMoney = buyMoney;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUseCondition() {
        return useCondition;
    }

    public void setUseCondition(String useCondition) {
        this.useCondition = useCondition;
    }

    public String getValidTime() {
        return validTime;
    }

    public void setValidTime(String validTime) {
        this.validTime = validTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getBackground() {
        return background;
    }

    public void setBackground(int background) {
        this.background = background;
    }

    @Override
    public Object getXBannerUrl() {
        return null;
    }

    @Override
    public String toString() {
        return "RedPackageBean{" +
                "id='" + id + '\'' +
                ", money='" + money + '\'' +
                ", buyMoney='" + buyMoney + '\'' +
                ", count='" + count + '\'' +
                ", type='" + type + '\'' +
                ", useCondition='" + useCondition + '\'' +
                ", validTime='" + validTime + '\'' +
                ", status='" + status + '\'' +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", tenantId='" + tenantId + '\'' +
                ", pic='" + pic + '\'' +
                ", name='" + name + '\'' +
                ", note='" + note + '\'' +
                ", background=" + background +
                ", userCode='" + userCode + '\'' +
                ", redPackedId='" + redPackedId + '\'' +
                ", payTime='" + payTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", payWay='" + payWay + '\'' +
                ", payMoney='" + payMoney + '\'' +
                ", tradeNo='" + tradeNo + '\'' +
                ", sellerId='" + sellerId + '\'' +
                '}';
    }
}
