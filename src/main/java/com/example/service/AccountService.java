package com.example.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    
    @Autowired
    private AccountRepository accountRepository;
  
    @Transactional
    public Account registerAccount(Account account){

        return accountRepository.save(account);
    }

    public Account logIntoAccount(String username, String password){

        return accountRepository.login(username, password);
    }
}
