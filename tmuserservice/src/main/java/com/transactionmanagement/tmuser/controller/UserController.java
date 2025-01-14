package com.transactionmanagement.tmuser.controller;

import com.transactionmanagement.tmuser.entity.User;
import com.transactionmanagement.tmuser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tm")
public class UserController {
    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getAllUser")
    public List<User> getUser()
    {
        return (List<User>) userService.getAllUser();
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/addBalance")
    public User addBalance(@RequestParam int balance)
    {
        return userService.addBalance(balance);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/getBalance")
    public int getBalance(@RequestHeader("Authorization") String token)
    {
        return userService.getBalance();
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/updateBalance")
    public int updateBalance(@RequestHeader("Authorization") String token,int balance)
    {
        return userService.updateBalance(balance);
    }
}
