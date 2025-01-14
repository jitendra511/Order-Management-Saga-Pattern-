package com.transactionmanagement.tmuser.service;

import com.transactionmanagement.tmuser.entity.User;
import com.transactionmanagement.tmuser.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public List<User> getAllUser()
    {
        return (List<User>) userRepository.findAll();
    }
    public User addBalance(int balance) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email);
        user.setBalance(user.getBalance() + balance);
        return userRepository.save(user);
    }
    public int getBalance()
    {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user=userRepository.findByEmail(email);
        return user.getBalance();
    }
    public int updateBalance(int balance)
    {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user=userRepository.findByEmail(email);
        user.setBalance(balance);
        userRepository.save(user);
        return user.getBalance();
    }
}
