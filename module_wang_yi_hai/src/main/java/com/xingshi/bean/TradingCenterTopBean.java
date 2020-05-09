package com.xingshi.bean;

import java.util.List;

public class TradingCenterTopBean {

    /**
     * totalPrice : 0.0
     * discount : [{"id":4,"price":13,"createTime":"2019-12-14 15:52:38"},{"id":5,"price":15,"createTime":"2019-12-15 15:52:49"},{"id":1,"price":10,"createTime":"2019-12-16 15:52:24"},{"id":2,"price":11,"createTime":"2019-12-17 15:52:30"},{"id":3,"price":12,"createTime":"2019-12-18 15:52:34"}]
     * totalServiceCharge : 100.0
     */

    private double totalPrice;
    private double totalServiceCharge;
    private List<DiscountBean> discount;

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getTotalServiceCharge() {
        return totalServiceCharge;
    }

    public void setTotalServiceCharge(double totalServiceCharge) {
        this.totalServiceCharge = totalServiceCharge;
    }

    public List<DiscountBean> getDiscount() {
        return discount;
    }

    public void setDiscount(List<DiscountBean> discount) {
        this.discount = discount;
    }

    public static class DiscountBean {
        /**
         * id : 4
         * price : 13.0
         * createTime : 2019-12-14 15:52:38
         */

        private int id;
        private double price;
        private String createTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }
}
