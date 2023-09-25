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

}
