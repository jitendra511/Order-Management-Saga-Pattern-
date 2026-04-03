package com.transactionmanagement.tmuser.service;

import com.transactionmanagement.tmuser.entity.User;
import com.transactionmanagement.tmuser.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @BeforeEach
    void setup()
    {
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
    }



    @Test
    public void testAddBalance()
    {
        User user=new User(1L,"Jitendra","Jitendr@","jitendra123@gmail.com",1000,"ADMIN");
        when(authentication.getName()).thenReturn(user.getEmail());
        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);
        when(userRepository.save(ArgumentMatchers.<User>any())).thenAnswer(invocation -> invocation.getArgument(0));

        int balanceToAdd=500;
        User updatedUser=userService.addBalance(balanceToAdd);
        Assertions.assertEquals(1500,updatedUser.getBalance());
    }

    @Test
    public void testGetBalance()
    {
        User user=new User(1L,"Jitendra","Jitendr@","jitendra123@gmail.com",1000,"ADMIN");
        when(authentication.getName()).thenReturn(user.getEmail());
        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);

        int result=userService.getBalance();
        Assertions.assertEquals(1000,result);
    }

    @Test
    public void testUpdateBalance()
    {
        User user=new User(1L,"Jitendra","Jitendr@","jitendra123@gmail.com",1000,"ADMIN");
        when(authentication.getName()).thenReturn(user.getEmail());
        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);

        int result=userService.updateBalance(2000);
        Assertions.assertEquals(2000,result);
    }
}
