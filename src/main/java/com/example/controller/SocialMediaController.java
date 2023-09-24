package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;



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
     * Request Body: A JSON of Account(username, password), does not contain account_id
     * 
     * Success if username -> not blank, password > 3 characters, username -> not existing in database
     * The new account will be persisted to the database and
     * Response body: JSON of the Account including its account_id.
     * Response status: 200 (Ok) if successful, 409 (Conflict) if username already exists, 400 (Client error) for any other reasons of unsuccessful registration.
     */
    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody Account credentials){

        Account account = accountService.registerAccount(credentials);
        HttpStatus status = HttpStatus.OK; 

        if(account.getUsername() == "Client error"){
            status = HttpStatus.BAD_REQUEST;
        }
        else if(account.getUsername() == "Conflict"){
            status = HttpStatus.CONFLICT;
        }
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






    // @GetMapping("/login")
    // public ResponseEntity<Account> login(
    //     @RequestParam String username, @RequestParam String password){

    //         AccountService accountService = new AccountService();
    //         Account account = accountService.logIntoAccount(username, password);

    //         return ResponseEntity.status(HttpStatus.OK).body(account);
    // }



    
}
