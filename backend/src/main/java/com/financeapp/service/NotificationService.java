package com.financeapp.service;

import com.financeapp.model.User;
import com.financeapp.model.CreditCard;
import com.financeapp.model.SpendingCategory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class NotificationService {
    
    public void sendCardRecommendation(User user, CreditCard optimalCard, 
                                     SpendingCategory category, BigDecimal amount) {
        // This would integrate with WebSocket, push notifications, or email service
        System.out.println(String.format(
            "💡 Hey %s! For your %s purchase of $%.2f, consider using your %s %s for better rewards!",
            user.getFirstName(),
            category.getDisplayName(),
            amount,
            optimalCard.getBankName(),
            optimalCard.getCardName()
        ));
    }
    
    public void sendBudgetAlert(User user, SpendingCategory category, Double currentSpending, Double budgetLimit) {
        System.out.println(String.format(
            "⚠️ Budget Alert: You've spent $%.2f on %s this month (%.1f%% of your $%.2f budget)",
            currentSpending,
            category.getDisplayName(),
            (currentSpending / budgetLimit) * 100,
            budgetLimit
        ));
    }
    
    public void sendRewardsUpdate(User user, BigDecimal totalCashBack, Integer totalPoints) {
        System.out.println(String.format(
            "🎉 Rewards Update: You've earned $%.2f cash back and %d points this month!",
            totalCashBack,
            totalPoints != null ? totalPoints : 0
        ));
    }
}
