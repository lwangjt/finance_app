package com.financeapp.dto;

import com.financeapp.model.SpendingCategory;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionRequest {
    
    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal amount;
    
    private String description;
    
    private String merchant;
    
    @NotNull
    private SpendingCategory category;
    
    private LocalDateTime transactionDate;
    
    private Long creditCardId;
    
    private Long groupId;
    
    // Constructors
    public TransactionRequest() {}
    
    // Getters and Setters
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getMerchant() { return merchant; }
    public void setMerchant(String merchant) { this.merchant = merchant; }
    
    public SpendingCategory getCategory() { return category; }
    public void setCategory(SpendingCategory category) { this.category = category; }
    
    public LocalDateTime getTransactionDate() { return transactionDate; }
    public void setTransactionDate(LocalDateTime transactionDate) { this.transactionDate = transactionDate; }
    
    public Long getCreditCardId() { return creditCardId; }
    public void setCreditCardId(Long creditCardId) { this.creditCardId = creditCardId; }
    
    public Long getGroupId() { return groupId; }
    public void setGroupId(Long groupId) { this.groupId = groupId; }
}
