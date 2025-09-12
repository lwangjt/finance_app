package com.financeapp.service;

import com.financeapp.model.CreditCard;
import com.financeapp.model.User;
import com.financeapp.model.SpendingCategory;
import com.financeapp.repository.CreditCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CreditCardService {
    
    @Autowired
    private CreditCardRepository creditCardRepository;
    
    public CreditCard addCreditCard(User user, String cardName, String bankName, 
                                   String lastFourDigits, CreditCard.CardType cardType, 
                                   BigDecimal cashBackRate, SpendingCategory bonusCategory, 
                                   BigDecimal bonusCashBackRate, BigDecimal annualFee) {
        CreditCard creditCard = new CreditCard(cardName, bankName, lastFourDigits, cardType, cashBackRate, user);
        creditCard.setBonusCategory(bonusCategory);
        creditCard.setBonusCashBackRate(bonusCashBackRate);
        creditCard.setAnnualFee(annualFee);
        
        return creditCardRepository.save(creditCard);
    }
    
    public List<CreditCard> getUserCreditCards(User user) {
        return creditCardRepository.findByUser(user);
    }
    
    public Optional<CreditCard> findOptimalCardForTransaction(User user, SpendingCategory category) {
        List<CreditCard> cards = creditCardRepository.findOptimalCardForCategory(user, category);
        return cards.isEmpty() ? Optional.empty() : Optional.of(cards.get(0));
    }
    
    public CreditCard findById(Long cardId) {
        return creditCardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Credit card not found"));
    }
    
    public BigDecimal calculateRewards(CreditCard card, SpendingCategory category, BigDecimal amount) {
        BigDecimal rate = (card.getBonusCategory() != null && card.getBonusCategory().equals(category)) 
                ? card.getBonusCashBackRate() 
                : card.getCashBackRate();
        
        return amount.multiply(rate != null ? rate : BigDecimal.ZERO);
    }
}
