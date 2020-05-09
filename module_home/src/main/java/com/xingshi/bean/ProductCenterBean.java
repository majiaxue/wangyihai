package com.xingshi.bean;

import java.io.Serializable;
import java.util.List;

public class ProductCenterBean implements Serializable {

    /**
     * records : [{"id":1,"logo":"http://47.99.93.123:8083/goods/248ebe8d45eb4ed7a341f17bc8fe6609.png","title":"测试产品","message":"这是简介","price":998,"info":"<p>好<img src=\"http://47.99.93.123:8083/goods/fa0ce1ed685741889dd56043afa6871f.png\" style=\"max-width:100%;\"><\/p><p><img src=\"http://47.99.93.123:8083/goods/94a969c9a28e40b9bd14b11e8c49a245.png\" style=\"max-width: 100%;\"><br><\/p><p><img src=\"http://47.99.93.123:8083/goods/f874cdd6d061477d8d42ffdf73d87306.png\" style=\"max-width:100%;\"><br><\/p>","pic":"http://47.99.93.123:8083/goods/669fb1205bd84cd496f382a14bbf8d2b.png,http://47.99.93.123:8083/goods/b19654f628454fff96535cd86a63fbec.png","createTime":"2019-11-22 16:08:33","updateTime":"2019-11-28 11:07:26","sort":1,"status":1,"phone":"12345678901","name":"销售A","categoryId":1,"testName":"APP测试地址;后台地址","testAddress":"www.aaa.com;www.ios.com,www.anzhuo.com","testAccount":"18888888888;18888888888,18888888888,18888888888,18888888888,18888888888,18888888888,18888888888,18888888888","testPassword":"18888888888;18888888888,18888888888,18888888888,18888888888,18888888888,18888888888,18888888888,18888888888","categoryName":null,"testNameList":[["APP测试地址"],["后台地址"]],"testAddressList":[["www.aaa.com"],["www.ios.com","www.anzhuo.com"]],"testAccountList":[["18888888888"],["18888888888","18888888888","18888888888","18888888888","18888888888","18888888888","18888888888","18888888888"]],"testPasswordList":[["18888888888"],["18888888888","18888888888","18888888888","18888888888","18888888888","18888888888","18888888888","18888888888"]]}]
     * total : 1
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

    @Override
    public String toString() {
        return "ProductCenterBean{" +
                "total=" + total +
                ", size=" + size +
                ", current=" + current +
                ", searchCount=" + searchCount +
                ", pages=" + pages +
                ", records=" + records +
                '}';
    }

    public static class RecordsBean implements Serializable {
        /**
         * id : 1
         * logo : http://47.99.93.123:8083/goods/248ebe8d45eb4ed7a341f17bc8fe6609.png
         * title : 测试产品
         * message : 这是简介
         * price : 998.0
         * info : <p>好<img src="http://47.99.93.123:8083/goods/fa0ce1ed685741889dd56043afa6871f.png" style="max-width:100%;"></p><p><img src="http://47.99.93.123:8083/goods/94a969c9a28e40b9bd14b11e8c49a245.png" style="max-width: 100%;"><br></p><p><img src="http://47.99.93.123:8083/goods/f874cdd6d061477d8d42ffdf73d87306.png" style="max-width:100%;"><br></p>
         * pic : http://47.99.93.123:8083/goods/669fb1205bd84cd496f382a14bbf8d2b.png,http://47.99.93.123:8083/goods/b19654f628454fff96535cd86a63fbec.png
         * createTime : 2019-11-22 16:08:33
         * updateTime : 2019-11-28 11:07:26
         * sort : 1
         * status : 1
         * phone : 12345678901
         * name : 销售A
         * categoryId : 1
         * testName : APP测试地址;后台地址
         * testAddress : www.aaa.com;www.ios.com,www.anzhuo.com
         * testAccount : 18888888888;18888888888,18888888888,18888888888,18888888888,18888888888,18888888888,18888888888,18888888888
         * testPassword : 18888888888;18888888888,18888888888,18888888888,18888888888,18888888888,18888888888,18888888888,18888888888
         * categoryName : null
         * testNameList : [["APP测试地址"],["后台地址"]]
         * testAddressList : [["www.aaa.com"],["www.ios.com","www.anzhuo.com"]]
         * testAccountList : [["18888888888"],["18888888888","18888888888","18888888888","18888888888","18888888888","18888888888","18888888888","18888888888"]]
         * testPasswordList : [["18888888888"],["18888888888","18888888888","18888888888","18888888888","18888888888","18888888888","18888888888","18888888888"]]
         */

        private String id;
        private String logo;
        private String title;
        private String message;
        private double price;
        private String info;
        private String pic;
        private String createTime;
        private String updateTime;
        private int sort;
        private int status;
        private String phone;
        private String name;
        private String categoryId;
        private String testName;
        private String testAddress;
        private String testAccount;
        private String testPassword;
        private String categoryName;
        private List<List<String>> testNameList;
        private List<List<String>> testAddressList;
        private List<List<String>> testAccountList;
        private List<List<String>> testPasswordList;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
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

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getTestName() {
            return testName;
        }

        public void setTestName(String testName) {
            this.testName = testName;
        }

        public String getTestAddress() {
            return testAddress;
        }

        public void setTestAddress(String testAddress) {
            this.testAddress = testAddress;
        }

        public String getTestAccount() {
            return testAccount;
        }

        public void setTestAccount(String testAccount) {
            this.testAccount = testAccount;
        }

        public String getTestPassword() {
            return testPassword;
        }

        public void setTestPassword(String testPassword) {
            this.testPassword = testPassword;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public List<List<String>> getTestNameList() {
            return testNameList;
        }

        public void setTestNameList(List<List<String>> testNameList) {
            this.testNameList = testNameList;
        }

        public List<List<String>> getTestAddressList() {
            return testAddressList;
        }

        public void setTestAddressList(List<List<String>> testAddressList) {
            this.testAddressList = testAddressList;
        }

        public List<List<String>> getTestAccountList() {
            return testAccountList;
        }

        public void setTestAccountList(List<List<String>> testAccountList) {
            this.testAccountList = testAccountList;
        }

        public List<List<String>> getTestPasswordList() {
            return testPasswordList;
        }

        public void setTestPasswordList(List<List<String>> testPasswordList) {
            this.testPasswordList = testPasswordList;
        }

        @Override
        public String toString() {
            return "RecordsBean{" +
                    "id='" + id + '\'' +
                    ", logo='" + logo + '\'' +
                    ", title='" + title + '\'' +
                    ", message='" + message + '\'' +
                    ", price=" + price +
                    ", info='" + info + '\'' +
                    ", pic='" + pic + '\'' +
                    ", createTime='" + createTime + '\'' +
                    ", updateTime='" + updateTime + '\'' +
                    ", sort=" + sort +
                    ", status=" + status +
                    ", phone='" + phone + '\'' +
                    ", name='" + name + '\'' +
                    ", categoryId='" + categoryId + '\'' +
                    ", testName='" + testName + '\'' +
                    ", testAddress='" + testAddress + '\'' +
                    ", testAccount='" + testAccount + '\'' +
                    ", testPassword='" + testPassword + '\'' +
                    ", categoryName='" + categoryName + '\'' +
                    ", testNameList=" + testNameList +
                    ", testAddressList=" + testAddressList +
                    ", testAccountList=" + testAccountList +
                    ", testPasswordList=" + testPasswordList +
                    '}';
        }
    }
}
