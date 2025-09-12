package com.financeapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    @DecimalMin(value = "0.01")
    @Column(name = "amount", precision = 10, scale = 2)
    private BigDecimal amount;
    
    @NotBlank
    @Column(name = "description")
    private String description;
    
    @NotBlank
    @Column(name = "merchant")
    private String merchant;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private SpendingCategory category;
    
    @NotNull
    @Column(name = "transaction_date")
    private LocalDateTime transactionDate;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "credit_card_id")
    private CreditCard creditCard;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;
    
    @Column(name = "cash_back_earned", precision = 10, scale = 2)
    private BigDecimal cashBackEarned;
    
    @Column(name = "points_earned")
    private Integer pointsEarned;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // Constructors
    public Transaction() {}
    
    public Transaction(BigDecimal amount, String description, String merchant, 
                      SpendingCategory category, LocalDateTime transactionDate, 
                      User user, CreditCard creditCard) {
        this.amount = amount;
        this.description = description;
        this.merchant = merchant;
        this.category = category;
        this.transactionDate = transactionDate;
        this.user = user;
        this.creditCard = creditCard;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
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
    
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    
    public CreditCard getCreditCard() { return creditCard; }
    public void setCreditCard(CreditCard creditCard) { this.creditCard = creditCard; }
    
    public Group getGroup() { return group; }
    public void setGroup(Group group) { this.group = group; }
    
    public BigDecimal getCashBackEarned() { return cashBackEarned; }
    public void setCashBackEarned(BigDecimal cashBackEarned) { this.cashBackEarned = cashBackEarned; }
    
    public Integer getPointsEarned() { return pointsEarned; }
    public void setPointsEarned(Integer pointsEarned) { this.pointsEarned = pointsEarned; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
