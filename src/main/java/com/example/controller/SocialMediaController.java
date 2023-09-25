package com.example.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
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

    /**
     * ACCOUNT REGISTRATION
     * Endpoint: GET localhost:8080/register
     * @RequestBody A JSON of an Account(username, password), does not contain account_id
     * Persist to Database: Conditions-> requested username -> not blank & not existing in database, password length > 3 characters
     * @ResponseBody JSON of the Account including its account_id.
     * @ResponseStatus 200 (Ok) if successful, 409 (Conflict) if username already exists, 400 (Client error) for any other reasons of unsuccessful registration.
     */
    @PostMapping("/register")
    public @ResponseBody ResponseEntity<Account> register(@RequestBody Account credentials) {

        Pair<Integer, Account> accService = accountService.registerAccount(credentials);
        return ResponseEntity.status(accService.getKey()).body(accService.getValue());
    }

    /**
     * ACCOUNT LOGIN
     * Endpoint: POST localhost:8080/login
     * @RequestBody A JSON of an Account.
     * 
     * Condition-> The requested username and password match an account existing on the database
     * @ResponseBody JSON of the Account including its account_id.
     * @ResponseStatus 200 (OK) if successful, 401 (Unauthorized) If not successful.
     */
    @PostMapping("/login")
    public @ResponseBody ResponseEntity<Account> login(@RequestBody Account credentials) {

        Account account = accountService.logIntoAccount(credentials);
        HttpStatus status = account==null ? HttpStatus.UNAUTHORIZED : HttpStatus.OK;

        return ResponseEntity.status(status).body(account);   
    }
   
    /**
     * MESSAGE CREATION
     * Endpoint: GET localhost:8080/messages
     * @RequestBody A JSON of an Message (username, password), does not contain message_id. 
     * Persist on Conditions: requested message_text>0 & <255 posted_by-> a user in database, 
     * 
     * @ResponseBody JSON of the Message including its message_id.
     * @ResponseStatus 200 (Ok) if successful, or 400 (Client error) if unsuccessful.
     */
    @PostMapping("/messages")
    public @ResponseBody ResponseEntity<Message> messageCreation(@RequestBody Message message) {

        Message newMessage = messageService.createMessage(message);
        HttpStatus status = newMessage==null ? HttpStatus.BAD_REQUEST : HttpStatus.OK;  

        return ResponseEntity.status(status).body(newMessage);
    }

    /**
     * MESSAGE RETRIEVING - ALL MESSAGES
     * Endpoint: GET localhost:8080/messages.
     * 
     * @ResponseBody JSON of a list containing all messages retrieved from the database or Empty list if there are no messages.
     * @ResponseStatus Always 200 (OK), which is the default.
     */
    @GetMapping("/messages")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody List<Message> getAllMessages() {
        return messageService.getAllMessages();
    }

    /**
     * MESSAGE RETRIEVING BY MESSAGE ID
     * Endpoint: GET localhost:8080/messages/{message_id}.
     * @PathVariable message_id
     * @ResponseBody JSON of the message identified by the message_id or Empty list if no message matches by Id.
     * @ResponseStatus Always 200 (OK), which is the default.
     */
    @GetMapping("/messages/{message_id}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Message getMessageById(@PathVariable int message_id) {
        return messageService.getMessageById(message_id);
    }

    /**
     * MESSAGE DELETION BY MESSAGE ID
     * Endpoint: DELETE localhost:8080/messages/{message_id}.
     * @PathVariable message_id
     * Delete on Condition: Only the requested message only if existed
     * @ResponseBody the number of rows updated (1) or 0 if the message does not exist
     * @ResponseStatus Always 200 (OK), which is the default.
     */
    @DeleteMapping("/messages/{message_id}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Integer deleteMessageById(@PathVariable int message_id) {
        return messageService.deleteMessageById(message_id);
    }

}
