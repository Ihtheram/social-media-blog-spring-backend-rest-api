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
     * GET ALL MESSAGES
     * 
     * Endpoint: GET localhost:8080/messages.
     * 
     * Response body: JSON of a list containing all messages retrieved from the database or Empty list if there are no messages.
     * Response body: Always 200, which is the default.
     */
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        
        return ResponseEntity.status(200).body(messageService.getAllMessages());
    }




    
    @PostMapping("register")
    public ResponseEntity<Account> register(
        @RequestParam String username, @RequestParam String password){

            AccountService accountService = new AccountService();
            Account account = accountService.registerAccount(new Account(username, password));

            return ResponseEntity.status(HttpStatus.OK).body(account);
    }

    @GetMapping("/login")
    public ResponseEntity<Account> login(
        @RequestParam String username, @RequestParam String password){

            AccountService accountService = new AccountService();
            Account account = accountService.logIntoAccount(username, password);

            return ResponseEntity.status(HttpStatus.OK).body(account);
    }



    
}
