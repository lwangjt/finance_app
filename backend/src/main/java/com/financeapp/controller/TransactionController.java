package com.financeapp.controller;

import com.financeapp.dto.TransactionRequest;
import com.financeapp.model.Transaction;
import com.financeapp.model.User;
import com.financeapp.model.SpendingCategory;
import com.financeapp.service.TransactionService;
import com.financeapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/transactions")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TransactionController {
    
    @Autowired
    private TransactionService transactionService;
    
    @Autowired
    private UserService userService;
    
    @PostMapping
    public ResponseEntity<Transaction> addTransaction(@Valid @RequestBody TransactionRequest request, 
                                                    Authentication authentication) {
        User user = userService.findByEmail(authentication.getName()).orElseThrow();
        
        LocalDateTime transactionDate = request.getTransactionDate() != null 
            ? request.getTransactionDate() 
            : LocalDateTime.now();
        
        Transaction transaction = transactionService.addTransaction(
            user,
            request.getAmount(),
            request.getDescription(),
            request.getMerchant(),
            request.getCategory(),
            transactionDate,
            request.getCreditCardId(),
            request.getGroupId()
        );
        
        return ResponseEntity.ok(transaction);
    }
    
    @GetMapping
    public ResponseEntity<List<Transaction>> getUserTransactions(Authentication authentication) {
        User user = userService.findByEmail(authentication.getName()).orElseThrow();
        List<Transaction> transactions = transactionService.getUserTransactions(user);
        return ResponseEntity.ok(transactions);
    }
    
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Transaction>> getTransactionsByCategory(@PathVariable SpendingCategory category, 
                                                                     Authentication authentication) {
        User user = userService.findByEmail(authentication.getName()).orElseThrow();
        List<Transaction> transactions = transactionService.getUserTransactionsByCategory(user, category);
        return ResponseEntity.ok(transactions);
    }
}
