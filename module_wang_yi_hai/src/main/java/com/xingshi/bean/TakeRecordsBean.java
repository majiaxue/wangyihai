package com.xingshi.bean;

import java.util.List;

public class TakeRecordsBean {

    /**
     * records : [{"id":3,"userCode":"293701212107177984","userName":"123456","bz":"用户升级奖励","taskName":"初级礼包","type":0,"createTime":"2019-12-21 11:34:50"},{"id":2,"userCode":"293701212107177984","userName":"123456","bz":"购买力宝","taskName":"初级礼包","type":1,"createTime":"2019-12-21 11:34:00"},{"id":1,"userCode":"293701212107177984","userName":"123456","bz":"激活赠送","taskName":"新手礼包","type":0,"createTime":"2019-12-21 11:33:01"}]
     * total : 3
     * size : 10
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
         * id : 3
         * userCode : 293701212107177984
         * userName : 123456
         * bz : 用户升级奖励
         * taskName : 初级礼包
         * type : 0
         * createTime : 2019-12-21 11:34:50
         */

        private int id;
        private String userCode;
        private String userName;
        private String bz;
        private String taskName;
        private int type;
        private String createTime;

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

        public String getBz() {
            return bz;
        }

        public void setBz(String bz) {
            this.bz = bz;
        }

        public String getTaskName() {
            return taskName;
        }

        public void setTaskName(String taskName) {
            this.taskName = taskName;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }
}
