package com.xingshi.bean;

import java.util.List;

public class YongJinTaskBean {

    /**
     * records : [{"id":5,"number":400,"ordinaryPrice":1,"shareholderPrice":1.5,"url":"123","operation":"123","verification":"123","bz":"123456","userCode":"293701212107177984","userName":"12345678","maxNumber":3,"createTime":"2019-12-16 14:57:38","endTime":"2020-12-17 00:00:00","title":"测试","price":0.5,"priceTotal":200,"surplusNum":428,"type":32,"status":"0","icon":null,"typeName":"注册"}]
     * total : 3
     * size : 10
     * current : 1
     * searchCount : true
     * pages : 1
     */

    private String total;
    private String size;
    private String current;
    private boolean searchCount;
    private String pages;
    private List<RecordsBean> records;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public boolean isSearchCount() {
        return searchCount;
    }

    public void setSearchCount(boolean searchCount) {
        this.searchCount = searchCount;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public List<RecordsBean> getRecords() {
        return records;
    }

    public void setRecords(List<RecordsBean> records) {
        this.records = records;
    }

    public static class RecordsBean {
        /**
         * id : 5
         * number : 400
         * ordinaryPrice : 1.0
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
         * priceTotal : 200.0
         * surplusNum : 428
         * type : 32
         * status : 0
         * icon : null
         * typeName : 注册
         */

        private String id;
        private String number;
        private double ordinaryPrice;
        private double shareholderPrice;
        private String url;
        private String operation;
        private String verification;
        private String bz;
        private String userCode;
        private String userName;
        private String maxNumber;
        private String createTime;
        private String endTime;
        private String title;
        private double price;
        private double priceTotal;
        private String surplusNum;
        private String type;
        private String status;
        private String icon;
        private String typeName;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
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

        public String getMaxNumber() {
            return maxNumber;
        }

        public void setMaxNumber(String maxNumber) {
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

        public String getSurplusNum() {
            return surplusNum;
        }

        public void setSurplusNum(String surplusNum) {
            this.surplusNum = surplusNum;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
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
}
