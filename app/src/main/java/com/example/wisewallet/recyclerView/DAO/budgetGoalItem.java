package com.example.wisewallet.recyclerView.DAO;

public class budgetGoalItem {
    String itemName;
    String budgetAmount;
    String currency;
    String savedUpAmount;
    String itemPrice;

    String budgetPeriod;
    String expectedDate;

    public budgetGoalItem(String itemName, String budgetAmount, String currency, String savedUpAmount, String itemPrice, String budgetPeriod, String expectedDate) {
        this.itemName = itemName;
        this.budgetAmount = budgetAmount;
        this.currency = currency;
        this.savedUpAmount = savedUpAmount;
        this.itemPrice = itemPrice;
        this.budgetPeriod = budgetPeriod;
        this.expectedDate = expectedDate;
    }

}
