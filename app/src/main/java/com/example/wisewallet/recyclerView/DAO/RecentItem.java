package com.example.wisewallet.recyclerView.DAO;

public class RecentItem {
    private String transactionName;
    private String transactionCatagory;
    private String transactionAmount;
    private String transactionType;
    private String transactionCurrency;
    private String transactionDate;


    // Constructor
    public RecentItem(String transactionName, String transactionCatagory, String transactionAmount, String transactionType, String transactionCurrency, String transactionDate) {
        this.transactionName = transactionName;
        this.transactionCatagory = transactionCatagory;
        this.transactionAmount = transactionAmount;
        this.transactionType = transactionType;
        this.transactionCurrency = transactionCurrency;
        this.transactionDate = transactionDate;
    }


    public String getTransactionName() {
        return transactionName;
    }
    public String getTransactionCatagory() {
        return transactionCatagory;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public String getTransactionCurrency() {
        return transactionCurrency;
    }


    public String getTransactionAmount() {
        return transactionAmount;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

}
