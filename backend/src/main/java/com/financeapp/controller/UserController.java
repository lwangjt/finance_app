package com.financeapp.controller;

import com.financeapp.model.User;
import com.financeapp.model.SpendingCategory;
import com.financeapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/categories")
    public ResponseEntity<List<SpendingCategory>> getAllCategories() {
        return ResponseEntity.ok(Arrays.asList(SpendingCategory.values()));
    }
    
    @PostMapping("/preferred-categories")
    public ResponseEntity<User> updatePreferredCategories(@RequestBody Set<SpendingCategory> categories, 
                                                         Authentication authentication) {
        User user = userService.findByEmail(authentication.getName()).orElseThrow();
        User updatedUser = userService.updatePreferredCategories(user.getId(), categories);
        return ResponseEntity.ok(updatedUser);
    }
    
    @GetMapping("/profile")
    public ResponseEntity<User> getUserProfile(Authentication authentication) {
        User user = userService.findByEmail(authentication.getName()).orElseThrow();
        return ResponseEntity.ok(user);
    }
}
