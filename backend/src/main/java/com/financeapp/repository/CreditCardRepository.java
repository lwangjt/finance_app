package com.financeapp.repository;

import com.financeapp.model.CreditCard;
import com.financeapp.model.User;
import com.financeapp.model.SpendingCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
    
    List<CreditCard> findByUser(User user);
    
    @Query("SELECT c FROM CreditCard c WHERE c.user = :user AND (c.bonusCategory = :category OR c.bonusCategory IS NULL) ORDER BY c.bonusCashBackRate DESC, c.cashBackRate DESC")
    List<CreditCard> findOptimalCardForCategory(@Param("user") User user, @Param("category") SpendingCategory category);
}
