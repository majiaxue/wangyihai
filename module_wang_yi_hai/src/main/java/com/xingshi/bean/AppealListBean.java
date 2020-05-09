package com.xingshi.bean;

import java.util.List;

public class AppealListBean {

    /**
     * records : [{"id":2,"orderNumber":"2019121310230681444893324592552","buyUserCode":"329064267913363456","buyUserName":"18639488962","buyPhone":"13201835918","sellUserCode":"293701212107177984","sellUserName":"12345678","sellPhone":"12345678","number":6,"price":8,"totalPrice":48,"createTime":"2019-12-13 14:47:48","status":0,"voucher":null,"paymentCode":"http://123456","openid":123456,"paymentTime":"2019-12-13 14:15:43","bz":"123456","paymentMethod":"支付宝","type":0,"currencyId":3,"frozen":0,"complaintuser":null}]
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
         * id : 2
         * orderNumber : 2019121310230681444893324592552
         * buyUserCode : 329064267913363456
         * buyUserName : 18639488962
         * buyPhone : 13201835918
         * sellUserCode : 293701212107177984
         * sellUserName : 12345678
         * sellPhone : 12345678
         * number : 6
         * price : 8.0
         * totalPrice : 48.0
         * createTime : 2019-12-13 14:47:48
         * status : 0
         * voucher : null
         * paymentCode : http://123456
         * openid : 123456
         * paymentTime : 2019-12-13 14:15:43
         * bz : 123456
         * paymentMethod : 支付宝
         * type : 0
         * currencyId : 3
         * frozen : 0
         * complaintuser : null
         */

        private int id;
        private String orderNumber;
        private String buyUserCode;
        private String buyUserName;
        private String buyPhone;
        private String sellUserCode;
        private String sellUserName;
        private String sellPhone;
        private int number;
        private double price;
        private double totalPrice;
        private String createTime;
        private int status;
        private String voucher;
        private String paymentCode;
        private String openid;
        private String paymentTime;
        private String bz;
        private String paymentMethod;
        private int type;
        private int currencyId;
        private int frozen;
        private String complaintuser;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getOrderNumber() {
            return orderNumber;
        }

        public void setOrderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
        }

        public String getBuyUserCode() {
            return buyUserCode;
        }

        public void setBuyUserCode(String buyUserCode) {
            this.buyUserCode = buyUserCode;
        }

        public String getBuyUserName() {
            return buyUserName;
        }

        public void setBuyUserName(String buyUserName) {
            this.buyUserName = buyUserName;
        }

        public String getBuyPhone() {
            return buyPhone;
        }

        public void setBuyPhone(String buyPhone) {
            this.buyPhone = buyPhone;
        }

        public String getSellUserCode() {
            return sellUserCode;
        }

        public void setSellUserCode(String sellUserCode) {
            this.sellUserCode = sellUserCode;
        }

        public String getSellUserName() {
            return sellUserName;
        }

        public void setSellUserName(String sellUserName) {
            this.sellUserName = sellUserName;
        }

        public String getSellPhone() {
            return sellPhone;
        }

        public void setSellPhone(String sellPhone) {
            this.sellPhone = sellPhone;
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

        public String getVoucher() {
            return voucher;
        }

        public void setVoucher(String voucher) {
            this.voucher = voucher;
        }

        public String getPaymentCode() {
            return paymentCode;
        }

        public void setPaymentCode(String paymentCode) {
            this.paymentCode = paymentCode;
        }

        public String getOpenid() {
            return openid;
        }

        public void setOpenid(String openid) {
            this.openid = openid;
        }

        public String getPaymentTime() {
            return paymentTime;
        }

        public void setPaymentTime(String paymentTime) {
            this.paymentTime = paymentTime;
        }

        public String getBz() {
            return bz;
        }

        public void setBz(String bz) {
            this.bz = bz;
        }

        public String getPaymentMethod() {
            return paymentMethod;
        }

        public void setPaymentMethod(String paymentMethod) {
            this.paymentMethod = paymentMethod;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getCurrencyId() {
            return currencyId;
        }

        public void setCurrencyId(int currencyId) {
            this.currencyId = currencyId;
        }

        public int getFrozen() {
            return frozen;
        }

        public void setFrozen(int frozen) {
            this.frozen = frozen;
        }

        public String getComplaintuser() {
            return complaintuser;
        }

        public void setComplaintuser(String complaintuser) {
            this.complaintuser = complaintuser;
        }
    }
}
