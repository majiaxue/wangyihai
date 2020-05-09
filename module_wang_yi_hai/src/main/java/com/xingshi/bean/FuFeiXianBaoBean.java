package com.xingshi.bean;

import java.util.List;

public class FuFeiXianBaoBean {

    /**
     * records : [{"id":15,"userCode":"293701212107177984","userName":"12345678","createTime":"2020-01-13 17:16:36","endTime":"2020-02-29 17:48:24","ordinaryPrice":1,"shareholderPrice":1,"title":"测试1231","pictureUrl":"http://47.99.93.123:8083/member/f44cfe2d41034604b7964f52895ef060.jpg","content":"123","status":"0","icon":null}]
     * total : 1
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
         * id : 15
         * userCode : 293701212107177984
         * userName : 12345678
         * createTime : 2020-01-13 17:16:36
         * endTime : 2020-02-29 17:48:24
         * ordinaryPrice : 1.0
         * shareholderPrice : 1.0
         * title : 测试1231
         * pictureUrl : http://47.99.93.123:8083/member/f44cfe2d41034604b7964f52895ef060.jpg
         * content : 123
         * status : 0
         * icon : null
         */

        private String id;
        private String userCode;
        private String userName;
        private String createTime;
        private String endTime;
        private double ordinaryPrice;
        private double shareholderPrice;
        private String title;
        private String pictureUrl;
        private String content;
        private String status;
        private String icon;

        public String getId() {
            return id;
        }

        public void setId(String id) {
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
    }
}
