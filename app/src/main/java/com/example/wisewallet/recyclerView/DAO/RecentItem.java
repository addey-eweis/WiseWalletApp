package com.example.wisewallet.recyclerView.DAO;

public class RecentItem {
    private String merchantText;
    private String categoryText;
    private String dollarText;
    private String dateText;

    // Constructor
    public RecentItem(String merchantText, String categoryText, String dollarText, String dateText) {
        this.merchantText = merchantText;
        this.categoryText = categoryText;
        this.dollarText = dollarText;
        this.dateText = dateText;
    }

    public String getMerchantText() {
        return merchantText;
    }
    public void setMerchantText(String merchantText) {
        this.merchantText = merchantText;
    }
    public String getCategoryText() {
        return categoryText;
    }
    public void setCategoryText(String categoryText) {
        this.categoryText = categoryText;
    }
    public String getDollarText() {
        return dollarText;
    }
    public void setDollarText(String dollarText) {
        this.dollarText = dollarText;
    }
    public String getDateText() {
        return dateText;
    }
    public void setDateText(String dateText) {
        this.dateText = dateText;
    }
}
