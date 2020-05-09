package com.xingshi.bean;

import java.util.List;

public class TestAccountBean {
    private String title;
    private List<String> addressList;
    private List<Account> accountList;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<String> addressList) {
        this.addressList = addressList;
    }

    public List<Account> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<Account> accountList) {
        this.accountList = accountList;
    }

    @Override
    public String toString() {
        return "TestAccountBean{" +
                "title='" + title + '\'' +
                ", addressList=" + addressList +
                ", accountList=" + accountList +
                '}';
    }

    public static class Account{
        private String account;
        private String password;

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        @Override
        public String toString() {
            return "Account{" +
                    "account='" + account + '\'' +
                    ", password='" + password + '\'' +
                    '}';
        }
    }
}
