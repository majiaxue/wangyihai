package com.xingshi.bean;

import java.util.List;

public class MyPayOrderAskToBuyBean {

    /**
     * records : [{"id":4,"userCode":"329064267913363456","userName":"18639488962","phone":"18639488962","number":10,"price":1,"totalPrice":10,"createTime":"2019-12-21 18:05:53","status":0,"icon":"http://47.99.93.123:8083/member/99f1b488559b4c45910e17187cac14b7.jpg","orderNumber":null}]
     * total : 1
     * size : 1
     * current : 1
     * searchCount : true
     * pages : 1
     */

    private int total;
    private int size;
    private int current;
    private boolean searchCount;
    private int pages;
    private List<RecordsBean> records;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public boolean isSearchCount() {
        return searchCount;
    }

    public void setSearchCount(boolean searchCount) {
        this.searchCount = searchCount;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
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
         * id : 4
         * userCode : 329064267913363456
         * userName : 18639488962
         * phone : 18639488962
         * number : 10
         * price : 1.0
         * totalPrice : 10.0
         * createTime : 2019-12-21 18:05:53
         * status : 0
         * icon : http://47.99.93.123:8083/member/99f1b488559b4c45910e17187cac14b7.jpg
         * orderNumber : null
         */

        private int id;
        private String userCode;
        private String userName;
        private String phone;
        private int number;
        private double price;
        private double totalPrice;
        private String createTime;
        private int status;
        private String icon;
        private String orderNumber;

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

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public double getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(double totalPrice) {
            this.totalPrice = totalPrice;
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

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getOrderNumber() {
            return orderNumber;
        }

        public void setOrderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
        }
    }
}
