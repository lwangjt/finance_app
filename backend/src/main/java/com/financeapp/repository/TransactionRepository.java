package com.financeapp.repository;

import com.financeapp.model.Transaction;
import com.financeapp.model.User;
import com.financeapp.model.SpendingCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    
    List<Transaction> findByUserOrderByTransactionDateDesc(User user);
    
    List<Transaction> findByUserAndCategoryOrderByTransactionDateDesc(User user, SpendingCategory category);
    
    List<Transaction> findByUserAndTransactionDateBetweenOrderByTransactionDateDesc(
        User user, LocalDateTime startDate, LocalDateTime endDate);
    
    @Query("SELECT t FROM Transaction t WHERE t.user = :user AND t.group.id = :groupId ORDER BY t.transactionDate DESC")
    List<Transaction> findByUserAndGroupIdOrderByTransactionDateDesc(@Param("user") User user, @Param("groupId") Long groupId);
    
    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.user = :user AND t.category = :category AND t.transactionDate >= :startDate")
    Double sumAmountByUserAndCategoryAndDateAfter(@Param("user") User user, 
                                                  @Param("category") SpendingCategory category, 
                                                  @Param("startDate") LocalDateTime startDate);
}
