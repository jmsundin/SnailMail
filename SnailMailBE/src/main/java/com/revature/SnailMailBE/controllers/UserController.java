package com.revature.SnailMailBE.controllers;

import com.revature.SnailMailBE.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //Make the class a bean, send HTTP responses in JSON format
@RequestMapping("/user") //Set the base URL to /user. Requests will be localhost:8080/user
@CrossOrigin //Allow requests from any origin (like our React FE)
public class UserController {

    @GetMapping
    public ResponseEntity<User> getUserInfo(){

        //Instantiate the User to send back (hardcoded)
        User u = new User(
                "SnailMailGuy123",
                "me@snailmail.com",
                "password",
                "user");

        //Return the user object
        return ResponseEntity.ok().body(u);
    }

}
