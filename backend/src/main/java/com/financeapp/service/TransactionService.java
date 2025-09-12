package com.financeapp.service;

import com.financeapp.model.Transaction;
import com.financeapp.model.User;
import com.financeapp.model.CreditCard;
import com.financeapp.model.SpendingCategory;
import com.financeapp.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    
    @Autowired
    private TransactionRepository transactionRepository;
    
    @Autowired
    private CreditCardService creditCardService;
    
    @Autowired
    private NotificationService notificationService;
    
    public Transaction addTransaction(User user, BigDecimal amount, String description, 
                                   String merchant, SpendingCategory category, 
                                   LocalDateTime transactionDate, Long creditCardId, Long groupId) {
        Transaction transaction = new Transaction(amount, description, merchant, category, transactionDate, user, null);
        
        if (creditCardId != null) {
            CreditCard creditCard = creditCardService.findById(creditCardId);
            transaction.setCreditCard(creditCard);
            
            // Calculate rewards
            BigDecimal cashBack = creditCardService.calculateRewards(creditCard, category, amount);
            transaction.setCashBackEarned(cashBack);
        }
        
        Transaction savedTransaction = transactionRepository.save(transaction);
        
        // Send recommendation notification
        Optional<CreditCard> optimalCard = creditCardService.findOptimalCardForTransaction(user, category);
        if (optimalCard.isPresent() && (creditCardId == null || !optimalCard.get().getId().equals(creditCardId))) {
            notificationService.sendCardRecommendation(user, optimalCard.get(), category, amount);
        }
        
        return savedTransaction;
    }
    
    public List<Transaction> getUserTransactions(User user) {
        return transactionRepository.findByUserOrderByTransactionDateDesc(user);
    }
    
    public List<Transaction> getUserTransactionsByCategory(User user, SpendingCategory category) {
        return transactionRepository.findByUserAndCategoryOrderByTransactionDateDesc(user, category);
    }
    
    public List<Transaction> getUserTransactionsByDateRange(User user, LocalDateTime startDate, LocalDateTime endDate) {
        return transactionRepository.findByUserAndTransactionDateBetweenOrderByTransactionDateDesc(user, startDate, endDate);
    }
    
    public Double getCategorySpending(User user, SpendingCategory category, LocalDateTime since) {
        Double spending = transactionRepository.sumAmountByUserAndCategoryAndDateAfter(user, category, since);
        return spending != null ? spending : 0.0;
    }
}
