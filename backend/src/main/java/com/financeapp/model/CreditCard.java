package com.financeapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "credit_cards")
public class CreditCard {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Column(name = "card_name")
    private String cardName;
    
    @NotBlank
    @Column(name = "bank_name")
    private String bankName;
    
    @NotBlank
    @Column(name = "last_four_digits")
    private String lastFourDigits;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "card_type")
    private CardType cardType;
    
    @DecimalMin(value = "0.0")
    @Column(name = "cash_back_rate", precision = 5, scale = 4)
    private BigDecimal cashBackRate;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "bonus_category")
    private SpendingCategory bonusCategory;
    
    @DecimalMin(value = "0.0")
    @Column(name = "bonus_cash_back_rate", precision = 5, scale = 4)
    private BigDecimal bonusCashBackRate;
    
    @Column(name = "annual_fee")
    private BigDecimal annualFee;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
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
    
    public enum CardType {
        VISA, MASTERCARD, AMERICAN_EXPRESS, DISCOVER
    }
    
    // Constructors
    public CreditCard() {}
    
    public CreditCard(String cardName, String bankName, String lastFourDigits, 
                     CardType cardType, BigDecimal cashBackRate, User user) {
        this.cardName = cardName;
        this.bankName = bankName;
        this.lastFourDigits = lastFourDigits;
        this.cardType = cardType;
        this.cashBackRate = cashBackRate;
        this.user = user;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getCardName() { return cardName; }
    public void setCardName(String cardName) { this.cardName = cardName; }
    
    public String getBankName() { return bankName; }
    public void setBankName(String bankName) { this.bankName = bankName; }
    
    public String getLastFourDigits() { return lastFourDigits; }
    public void setLastFourDigits(String lastFourDigits) { this.lastFourDigits = lastFourDigits; }
    
    public CardType getCardType() { return cardType; }
    public void setCardType(CardType cardType) { this.cardType = cardType; }
    
    public BigDecimal getCashBackRate() { return cashBackRate; }
    public void setCashBackRate(BigDecimal cashBackRate) { this.cashBackRate = cashBackRate; }
    
    public SpendingCategory getBonusCategory() { return bonusCategory; }
    public void setBonusCategory(SpendingCategory bonusCategory) { this.bonusCategory = bonusCategory; }
    
    public BigDecimal getBonusCashBackRate() { return bonusCashBackRate; }
    public void setBonusCashBackRate(BigDecimal bonusCashBackRate) { this.bonusCashBackRate = bonusCashBackRate; }
    
    public BigDecimal getAnnualFee() { return annualFee; }
    public void setAnnualFee(BigDecimal annualFee) { this.annualFee = annualFee; }
    
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
