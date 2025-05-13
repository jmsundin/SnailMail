package com.revature.SnailMailBE.daos;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.revature.SnailMailBE.models.Mail;

public interface MailDAO extends MongoRepository<Mail, String> {
    
}
