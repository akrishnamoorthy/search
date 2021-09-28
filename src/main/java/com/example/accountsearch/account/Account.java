package com.example.accountsearch.account;

public class Account {

    public Account(String id, String name, String address){
        this.accountId=id;
        this.accountName= name;
        this.address=address;
    }

    private String accountId;

    private String accountName;

    private String address;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
