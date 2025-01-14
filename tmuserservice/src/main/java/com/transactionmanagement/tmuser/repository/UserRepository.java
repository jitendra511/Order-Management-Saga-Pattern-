package com.transactionmanagement.tmuser.repository;

import com.sun.jdi.LongValue;
import com.transactionmanagement.tmuser.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public User findByEmail(String email);
}
