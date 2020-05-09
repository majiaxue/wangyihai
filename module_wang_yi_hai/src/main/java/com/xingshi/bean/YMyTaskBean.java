package com.xingshi.bean;

import java.util.List;

public class YMyTaskBean {

    /**
     * records : [{"id":4,"userCode":"329064267913363456","phone":"18639488962","userName":"18639488962","taskName":"新人礼包","taskId":1,"consume":0,"profit":16,"cycle":40,"everydayProfit":0.4,"createTime":"2019-12-18 16:17:01","taskContent":1,"completeDay":0,"surplusDay":39,"surplusNumber":1,"status":0}]
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
         * phone : 18639488962
         * userName : 18639488962
         * taskName : 新人礼包
         * taskId : 1
         * consume : 0
         * profit : 16
         * cycle : 40
         * everydayProfit : 0.4
         * createTime : 2019-12-18 16:17:01
         * taskContent : 1
         * completeDay : 0
         * surplusDay : 39
         * surplusNumber : 1
         * status : 0
         */

        private int id;
        private String userCode;
        private String phone;
        private String userName;
        private String taskName;
        private int taskId;
        private int consume;
        private int profit;
        private int cycle;
        private double everydayProfit;
        private String createTime;
        private int taskContent;
        private int completeDay;
        private int surplusDay;
        private int surplusNumber;
        private int status;
        private String picture;

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

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

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getTaskName() {
            return taskName;
        }

        public void setTaskName(String taskName) {
            this.taskName = taskName;
        }

        public int getTaskId() {
            return taskId;
        }

        public void setTaskId(int taskId) {
            this.taskId = taskId;
        }

        public int getConsume() {
            return consume;
        }

        public void setConsume(int consume) {
            this.consume = consume;
        }

        public int getProfit() {
            return profit;
        }

        public void setProfit(int profit) {
            this.profit = profit;
        }

        public int getCycle() {
            return cycle;
        }

        public void setCycle(int cycle) {
            this.cycle = cycle;
        }

        public double getEverydayProfit() {
            return everydayProfit;
        }

        public void setEverydayProfit(double everydayProfit) {
            this.everydayProfit = everydayProfit;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getTaskContent() {
            return taskContent;
        }

        public void setTaskContent(int taskContent) {
            this.taskContent = taskContent;
        }

        public int getCompleteDay() {
            return completeDay;
        }

        public void setCompleteDay(int completeDay) {
            this.completeDay = completeDay;
        }

        public int getSurplusDay() {
            return surplusDay;
        }

        public void setSurplusDay(int surplusDay) {
            this.surplusDay = surplusDay;
        }

        public int getSurplusNumber() {
            return surplusNumber;
        }

        public void setSurplusNumber(int surplusNumber) {
            this.surplusNumber = surplusNumber;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
