package com.xingshi.bean;

public class YTaskBean {

    /**
     * id : 1
     * taskLevel : 新人礼包
     * content : 1
     * consume : 0
     * profit : 16
     * cycle : 40
     * everydayProfit : 0.4
     * createTime : 2019-12-12 17:08:29
     * number : 0
     * picture :
     */

    private int id;
    private String taskLevel;
    private int content;
    private int consume;
    private int profit;
    private int cycle;
    private double everydayProfit;
    private String createTime;
    private int number;
    private String picture;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskLevel() {
        return taskLevel;
    }

    public void setTaskLevel(String taskLevel) {
        this.taskLevel = taskLevel;
    }

    public int getContent() {
        return content;
    }

    public void setContent(int content) {
        this.content = content;
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

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
