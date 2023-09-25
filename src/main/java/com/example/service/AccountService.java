package com.example.service;

import java.util.List;
import javafx.util.Pair;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    
    @Autowired
    private AccountRepository accountRepository;
  
    /**
     * ACCOUNT REGISTRATION
     * @Param credentials - requested username and password
     * Validating-> requested username -> 'not blank' & 'not existing in database', password length > 3 characters
     * @return A Pair of 'Integer of status code' and 'persisted new Account' if conditions met, 'null' otherwise
     */
    @Transactional
    public Pair<Integer, Account> registerAccount(Account credentials){
        List<Account> accList = accountRepository.findAll();
    
        // Conditions check
        if(credentials.getUsername()==null && credentials.getPassword().length()<4){
            return new Pair<>(400, null);  
        }
        // Duplicate Username check
        for(Account acc : accList) {
            if(acc.getUsername().equals(credentials.getUsername())){
                return new Pair<>(409, null);  
            }
        }
        // If all conditions met, persist to database and return account
        return new Pair<>(200, accountRepository.save(credentials));
    }

    /**
     * ACCOUNT LOGIN
     * @Param credentials - requested username and password
     * Validating -> The requested username and password match an existing account on the database
     * @return An Account if matched with the given credentials, null otherwise
     */
    @Transactional
    public Account logIntoAccount(Account credentials){
        List<Account> accounts = accountRepository.findAll();

        // Username and Password validation
        for(Account account : accounts) {
            if(account.getUsername().equals(credentials.getUsername())
            && account.getPassword().equals(credentials.getPassword())) {
                return account;
            }
        }
        // Username and/or Password dont match a real account
        return null;    
    }
}