package com.transactionmanagement.tmuser.controller;

import com.transactionmanagement.tmuser.controller.UserController;
import com.transactionmanagement.tmuser.entity.User;
import com.transactionmanagement.tmuser.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;


    @Test
    public void testAddBalance()
    {
        User user=new User(1L,"Jitendra","Jitendr@","jitendra123@gmail.com",1000,"ADMIN");
        when(userService.addBalance(500)).thenReturn(user);
        User result=userController.addBalance(500);
        Assertions.assertEquals(1000,result.getBalance());
    }

    @Test
    public void testGetBalance()
    {
        User user=new User(1L,"Jitendra","Jitendr@","jitendra123@gmail.com",1000,"ADMIN");
        when(userService.getBalance()).thenReturn(user.getBalance());
        int result=userController.getBalance("token");
        Assertions.assertEquals(1000,result);
    }


}
