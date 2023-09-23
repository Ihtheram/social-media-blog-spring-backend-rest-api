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
    AccountRepository accountRepository;

    
    // public AccountService(AccountRepository accountRepository){
    //     this.accountRepository = accountRepository;
    // }
    
    @Transactional
    public Account registerAccount(Account account){

        return accountRepository.save(account);
    }

    public Account logIntoAccount(String username, String password){
        //findById returns a type Optional<Grocery>. This helps the developer avoid null pointer
        //exceptions. We can use the method .get() to convert an Optional<Grocery> to Grocery.
        return accountRepository.login(username, password);
    }
}
