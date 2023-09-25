package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private AccountRepository accountRepository;
    
    /**
     * MESSAGE CREATION
     * @Param A Message (posted_by, message_text, time_posted_epoch) does not contain message_id. 
     * Validating-> requested message_text>0 & <255 posted_by-> a user in database, 
     * @return `persisted new Account` if conditions met, `null` otherwise
     */
    @Transactional
    public Message createMessage(Message message){
        List<Account> accounts = accountRepository.findAll();

        if (message.getMessage_text().length() > 0 && message.getMessage_text().length() < 255) {
            for (Account account : accounts) {
                if (account.getAccount_id().equals(message.getPosted_by())) {
                    return messageRepository.save(message);
                }
            }
        }
        return null; 
    }

    /**
     * MESSAGE RETRIEVING - ALL MESSAGES
     * @return `List of Messages` if exists `empty List` otherwise
     */
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    /**
     * MESSAGE RETRIEVING BY MESSAGE ID
     * @Param message_id - requested message id
     * Validating -> The requested message id matches an existing message id in the database
     * @return a message if matched with the given id, null otherwise
     */
    public Message getMessageById(int message_id){
        List<Message> messages = messageRepository.findAll();

        for(Message message : messages) {
            if(message.getMessage_id() == message_id) {
                return message;
            }
        }
        // Message Id does not match with any of the ones in the database
        return null;    
    }

    /**
     * MESSAGE DELETION BY MESSAGE ID
     * @Param message_id - requested message id
     * Validating -> The requested message id matches an existing message id in the database
     * @return the number of rows updated (1) or 0 if the message does not exist
     */
    public Integer deleteMessageById(int message_id){
        List<Message> messages = messageRepository.findAll();

        Integer rows_updated = 0;
        for(Message message : messages) {
            if(message.getMessage_id() == message_id) {
                messageRepository.delete(message);
                rows_updated++;
            }
        }
        return rows_updated;    
    }

    /**
     * MESSAGE UPDATE
     * @Param newMessage requested Message contains message_text to add to the update
     * @Param message_id requested id of the message to be modified
     * Validating -> The requested message id exists in database and message_text>0 & <255 , 
     * @return the number of rows updated (1) or 0 if the message does not exist
     */
    public Integer updateMessage(Message newMessage, int message_id) {
        List<Message> messages = messageRepository.findAll();
        Integer rows_updated = 0;

        if (newMessage.getMessage_text().length() > 0 && newMessage.getMessage_text().length() < 255) {
            for (Message message : messages) {
                if (message.getMessage_id() == message_id) {
                    message.setMessage_text(newMessage.getMessage_text());
                    messageRepository.save(message);
                    rows_updated++;
                }
            }
        }
        return rows_updated;
    }
    
}
