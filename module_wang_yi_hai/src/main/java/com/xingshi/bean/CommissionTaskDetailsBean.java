package com.xingshi.bean;

import java.util.List;

public class CommissionTaskDetailsBean {

    /**
     * Img : [{"id":1,"commissionId":4,"imgUrl":null},{"id":2,"commissionId":4,"imgUrl":null}]
     * commission : {"id":4,"number":400,"ordinaryPrice":1,"shareholderPrice":1.5,"url":"123","operation":"123","verification":"123","bz":"123456","userCode":"293701212107177984","userName":"12345678","maxNumber":3,"createTime":"2019-12-16 14:57:38","endTime":"2020-12-17 00:00:00","title":"测试","price":0.5,"priceTotal":600,"surplusNum":399,"type":32,"icon":null,"typeName":"注册"}
     */

    private CommissionBean commission;
    private List<ImgBean> Img;

    public CommissionBean getCommission() {
        return commission;
    }

    public void setCommission(CommissionBean commission) {
        this.commission = commission;
    }

    public List<ImgBean> getImg() {
        return Img;
    }

    public void setImg(List<ImgBean> Img) {
        this.Img = Img;
    }

    public static class CommissionBean {
        /**
         * id : 4
         * number : 400
         * ordinaryPrice : 1
         * shareholderPrice : 1.5
         * url : 123
         * operation : 123
         * verification : 123
         * bz : 123456
         * userCode : 293701212107177984
         * userName : 12345678
         * maxNumber : 3
         * createTime : 2019-12-16 14:57:38
         * endTime : 2020-12-17 00:00:00
         * title : 测试
         * price : 0.5
         * priceTotal : 600
         * surplusNum : 399
         * type : 32
         * icon : null
         * typeName : 注册
         */

        private int id;
        private int number;
        private double ordinaryPrice;
        private double shareholderPrice;
        private String url;
        private String operation;
        private String verification;
        private String bz;
        private String userCode;
        private String userName;
        private int maxNumber;
        private String createTime;
        private String endTime;
        private String title;
        private double price;
        private double priceTotal;
        private int surplusNum;
        private int type;
        private String icon;
        private String typeName;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
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

        public String getOperation() {
            return operation;
        }

        public void setOperation(String operation) {
            this.operation = operation;
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

        public int getMaxNumber() {
            return maxNumber;
        }

        public void setMaxNumber(int maxNumber) {
            this.maxNumber = maxNumber;
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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public double getPriceTotal() {
            return priceTotal;
        }

        public void setPriceTotal(double priceTotal) {
            this.priceTotal = priceTotal;
        }

        public int getSurplusNum() {
            return surplusNum;
        }

        public void setSurplusNum(int surplusNum) {
            this.surplusNum = surplusNum;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }
    }

    public static class ImgBean {
        /**
         * id : 1
         * commissionId : 4
         * imgUrl : null
         */

        private int id;
        private int commissionId;
        private String imgUrl;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCommissionId() {
            return commissionId;
        }

        public void setCommissionId(int commissionId) {
            this.commissionId = commissionId;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }
    }
}
