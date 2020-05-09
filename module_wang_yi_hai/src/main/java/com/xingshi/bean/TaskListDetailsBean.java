package com.xingshi.bean;

import java.util.List;

public class TaskListDetailsBean {

    /**
     * Img : [{"id":1,"commissionExamineId":4,"imgUrl":"1234"},{"id":2,"commissionExamineId":4,"imgUrl":"1234"}]
     * commissionExamine : {"id":4,"userCode":"329064267913363456","userName":"18639488962","phone":"18639488962","taskType":"32","number":"1","taskName":null,"taskUnitPrice":null,"createTime":null,"status":2,"releaseUserCode":"2","releaseUserName":null,"endTime":null,"ordinaryPrice":10,"shareholderPrice":2,"url":null,"explains":null,"verification":null,"bz":null,"subStatus":1,"commissionId":2,"payStatus":1,"payWay":null,"payTime":"2019-12-26 15:12:57","outTradeNo":null,"realMoney":1,"num":3,"completeTime":"2019-12-26 15:13:05","backStatus":0,"timeOut":null,"icon":null}
     */

    private CommissionExamineBean commissionExamine;
    private List<ImgBean> Img;

    public CommissionExamineBean getCommissionExamine() {
        return commissionExamine;
    }

    public void setCommissionExamine(CommissionExamineBean commissionExamine) {
        this.commissionExamine = commissionExamine;
    }

    public List<ImgBean> getImg() {
        return Img;
    }

    public void setImg(List<ImgBean> Img) {
        this.Img = Img;
    }

    public static class CommissionExamineBean {
        /**
         * id : 4
         * userCode : 329064267913363456
         * userName : 18639488962
         * phone : 18639488962
         * taskType : 32
         * number : 1
         * taskName : null
         * taskUnitPrice : null
         * createTime : null
         * status : 2
         * releaseUserCode : 2
         * releaseUserName : null
         * endTime : null
         * ordinaryPrice : 10.0
         * shareholderPrice : 2.0
         * url : null
         * explains : null
         * verification : null
         * bz : null
         * subStatus : 1
         * commissionId : 2
         * payStatus : 1
         * payWay : null
         * payTime : 2019-12-26 15:12:57
         * outTradeNo : null
         * realMoney : 1.0
         * num : 3
         * completeTime : 2019-12-26 15:13:05
         * backStatus : 0
         * timeOut : null
         * icon : null
         */

        private int id;
        private String userCode;
        private String userName;
        private String phone;
        private String taskType;
        private String number;
        private String taskName;
        private double taskUnitPrice;
        private String createTime;
        private int status;
        private String releaseUserCode;
        private String releaseUserName;
        private String endTime;
        private double ordinaryPrice;
        private double shareholderPrice;
        private String url;
        private String explains;
        private String verification;
        private String bz;
        private int subStatus;
        private int commissionId;
        private int payStatus;
        private String payWay;
        private String payTime;
        private String outTradeNo;
        private double realMoney;
        private int num;
        private String completeTime;
        private int backStatus;
        private String timeOut;
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

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getTaskType() {
            return taskType;
        }

        public void setTaskType(String taskType) {
            this.taskType = taskType;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getTaskName() {
            return taskName;
        }

        public void setTaskName(String taskName) {
            this.taskName = taskName;
        }

        public double getTaskUnitPrice() {
            return taskUnitPrice;
        }

        public void setTaskUnitPrice(double taskUnitPrice) {
            this.taskUnitPrice = taskUnitPrice;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getReleaseUserCode() {
            return releaseUserCode;
        }

        public void setReleaseUserCode(String releaseUserCode) {
            this.releaseUserCode = releaseUserCode;
        }

        public String getReleaseUserName() {
            return releaseUserName;
        }

        public void setReleaseUserName(String releaseUserName) {
            this.releaseUserName = releaseUserName;
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

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getExplains() {
            return explains;
        }

        public void setExplains(String explains) {
            this.explains = explains;
        }

        public String getVerification() {
            return verification;
        }

        public void setVerification(String verification) {
            this.verification = verification;
        }

        public String getBz() {
            return bz;
        }

        public void setBz(String bz) {
            this.bz = bz;
        }

        public int getSubStatus() {
            return subStatus;
        }

        public void setSubStatus(int subStatus) {
            this.subStatus = subStatus;
        }

        public int getCommissionId() {
            return commissionId;
        }

        public void setCommissionId(int commissionId) {
            this.commissionId = commissionId;
        }

        public int getPayStatus() {
            return payStatus;
        }

        public void setPayStatus(int payStatus) {
            this.payStatus = payStatus;
        }

        public String getPayWay() {
            return payWay;
        }

        public void setPayWay(String payWay) {
            this.payWay = payWay;
        }

        public String getPayTime() {
            return payTime;
        }

        public void setPayTime(String payTime) {
            this.payTime = payTime;
        }

        public String getOutTradeNo() {
            return outTradeNo;
        }

        public void setOutTradeNo(String outTradeNo) {
            this.outTradeNo = outTradeNo;
        }

        public double getRealMoney() {
            return realMoney;
        }

        public void setRealMoney(double realMoney) {
            this.realMoney = realMoney;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getCompleteTime() {
            return completeTime;
        }

        public void setCompleteTime(String completeTime) {
            this.completeTime = completeTime;
        }

        public int getBackStatus() {
            return backStatus;
        }

        public void setBackStatus(int backStatus) {
            this.backStatus = backStatus;
        }

        public String getTimeOut() {
            return timeOut;
        }

        public void setTimeOut(String timeOut) {
            this.timeOut = timeOut;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
    }

    public static class ImgBean {
        /**
         * id : 1
         * commissionExamineId : 4
         * imgUrl : 1234
         */

        private int id;
        private int commissionExamineId;
        private String imgUrl;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCommissionExamineId() {
            return commissionExamineId;
        }

        public void setCommissionExamineId(int commissionExamineId) {
            this.commissionExamineId = commissionExamineId;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }
    }
}
