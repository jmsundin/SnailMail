package com.revature.SnailMailBE.controllers;

import com.revature.SnailMailBE.models.Mail;
import com.revature.SnailMailBE.services.MailService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Collections;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpServletRequest;

//Remember the 3 annotations that we include for a Spring MVC Controller:
@RestController //Makes the Class a Bean, and turns response data into JSON
@RequestMapping("/mail") //Sets the base URL to reach this controller (it'll be http://localhost:8080/mail)
@CrossOrigin //Allows requests from any origin (this will let our FE/BE communicate)
public class MailController {

    //Because the MailController depends on the Service, we must inject it
    //We do this so we can use its methods
    private MailService mailService;

    //Constructor Injection - best practice for autowiring
    @Autowired
    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    //This method sends a user's inbox back to them (a List of Mail objects)
    @GetMapping
    public ResponseEntity<List<Mail>> getInbox(){

        //Send a request to the service layer to get the inbox
        List<Mail> inbox = mailService.getInbox();

        //Easily configure and return an HTTP response thanks to ResponseEntity
        //200 level status code, or 204 status code if inbox is empty
        if(inbox == null){
            return ResponseEntity.noContent().build(); //204 status code
        } else {
            return ResponseEntity.ok().body(inbox); //200 status code
        }

    }

    //This method will take in a Mail object and send a (fake) email
    @PostMapping(produces = "application/json")
    public ResponseEntity<?> sendMail(@RequestBody Mail mail, HttpServletRequest request) {
        if (mail == null) {
            return ResponseEntity.badRequest().body(
                java.util.Map.of("error", "Request body is missing or invalid")
            );
        }
        if (mail.getRecipient() == null || mail.getRecipient().isBlank()) {
            return ResponseEntity.badRequest().body(
                java.util.Map.of("error", "Recipient cannot be empty!")
            );
        }

        if (mail.getSubject() == null || mail.getSubject().length() > 20) {
            return ResponseEntity.badRequest().body(
                java.util.Map.of("error", "Save it for the message body, buddy")
            );
        }

        // Optionally, log headers if needed
        // System.out.println("Headers: " + Collections.list(request.getHeaderNames())
        //     .stream()
        //     .collect(Collectors.toMap(h -> h, request::getHeader)));

        // Use the service to process and return the result
        return ResponseEntity.ok().body(mailService.sendMail(mail));
    }

    //Spring MVC ExceptionHandler - super generic one to help with responses/tests
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        System.out.println(e.getMessage());
        return ResponseEntity.badRequest().body(
            java.util.Map.of("error", e.getMessage())
        );
    }

}