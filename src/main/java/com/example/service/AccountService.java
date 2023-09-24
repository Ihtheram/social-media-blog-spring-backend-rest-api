package com.example.service;

import java.util.List;
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
    public Account registerAccount(Account credentials){
        List<Account> accList = accountRepository.findAll();
        Account newAcc = credentials;

        // Duplicate Username check
        for(Account acc : accList) {
            if(acc.getUsername().equals(newAcc.getUsername())){
                newAcc.setUsername("Conflict");
            }
        }
        // Meets all requirements
        if(newAcc.getUsername()!=null && newAcc.getPassword().length()>3){
            return accountRepository.save(newAcc);
        }
        // if unsuccessful for any other reasons
        else{
            newAcc.setUsername("Client Error");
        }   
        return newAcc;    
    }

    // public Account logIntoAccount(String username, String password){

    //     return accountRepository.login(username, password);
    // }
}
