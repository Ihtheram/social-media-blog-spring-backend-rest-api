package com.example.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

import javafx.util.Pair;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private MessageService messageService;

    /*
     * ACCOUNT REGISTRATION
     * 
     * Endpoint: GET localhost:8080/register
     * Request Body: A JSON of an Account(username, password), does not contain account_id
     * 
     * Conditions-> requested username -> not blank & not existing in database, password length > 3 characters
     * Persist to Database: The new account, if conditions met
     * Response body: JSON of the Account including its account_id.
     * Response status: 200 (Ok) if successful, 409 (Conflict) if username already exists, 400 (Client error) for any other reasons of unsuccessful registration.
     */
    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody Account credentials){

        Pair<Integer, Account> accService = accountService.registerAccount(credentials);
        return ResponseEntity.status(accService.getKey()).body(accService.getValue());
    }

    /*
     * ACCOUNT LOGIN
     * Endpoint: POST localhost:8080/login
     * Request body: A JSON of an Account.
     * 
     * Condition-> The requested username and password match an account existing on the database
     * Response body: JSON of the Account including its account_id.
     * Response status: 200 (OK) if successful, 401 (Unauthorized) If not successful.
     */
    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account credentials){

        Account account = accountService.logIntoAccount(credentials);
        HttpStatus status = account==null ? HttpStatus.UNAUTHORIZED : HttpStatus.OK;

        return ResponseEntity.status(status).body(account);   
    }

    /*
     * GET ALL MESSAGES
     * 
     * Endpoint: GET localhost:8080/messages.
     * 
     * Response body: JSON of a list containing all messages retrieved from the database or Empty list if there are no messages.
     * Response status: Always 200, which is the default.
     */
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        
        return ResponseEntity.status(200).body(messageService.getAllMessages());
    }
    
}
