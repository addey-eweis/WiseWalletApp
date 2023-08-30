package com.example.wisewallet.recyclerView.DAO;

public class BudgetGoalItem {
    String goalName;
    String budgetAmount;
    String currency;
    String savedUpAmount;
    String goalAmount;
    String budgetPeriod;
    String expectedDate;

    public BudgetGoalItem(String goalName, String budgetAmount, String currency, String savedUpAmount, String goalAmount, String budgetPeriod, String expectedDate) {
        this.goalName = goalName;
        this.budgetAmount = budgetAmount;
        this.currency = currency;
        this.savedUpAmount = savedUpAmount;
        this.goalAmount = goalAmount;
        this.budgetPeriod = budgetPeriod;
        this.expectedDate = expectedDate;
    }

    public String getGoalName() {
        return goalName;
    }

    public String getBudgetAmount() {
        return budgetAmount;
    }

    public String getCurrency() {
        return currency;
    }

    public String getSavedUpAmount() {
        return savedUpAmount;
    }

    public String getGoalAmount() {
        return goalAmount;
    }

    public String getBudgetPeriod() {
        return budgetPeriod;
    }

    public String getExpectedDate() {
        return expectedDate;
    }






}
