package com.financeapp.model;

public enum SpendingCategory {
    GROCERIES("Groceries"),
    DINING("Dining"),
    GAS("Gas"),
    TRAVEL("Travel"),
    ENTERTAINMENT("Entertainment"),
    SHOPPING("Shopping"),
    UTILITIES("Utilities"),
    HEALTHCARE("Healthcare"),
    EDUCATION("Education"),
    TRANSPORTATION("Transportation"),
    OTHER("Other");
    
    private final String displayName;
    
    SpendingCategory(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}
