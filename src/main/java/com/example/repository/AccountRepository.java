package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.Account;

@Repository
@EnableJpaRepositories
public interface AccountRepository extends JpaRepository<Account, Integer> {

    @Query("from Account where username = :username and password = :password")
    Account login(@Param("username") String username, @Param("password") String password);
}