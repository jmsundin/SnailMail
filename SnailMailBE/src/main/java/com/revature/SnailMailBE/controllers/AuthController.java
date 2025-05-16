package com.revature.SnailMailBE.controllers;

import com.revature.SnailMailBE.models.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController //makes the class a bean, turns response data into JSON
@RequestMapping("/auth") //Any request ending in /auth will come to this controller
@CrossOrigin //allow requests from any origin
public class AuthController {

    private static final Map<String, String> users = Map.of(
        "username", "password",
        "alice", "alice123",
        "bob", "bobpass"
    );

    //Login - POST request to /auth/login
    //ResponseEntity<?> means we can send any type of data back
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User incomingUser, HttpSession session){

        //NOTE the HttpSession object in the param.
        //We can initialize it and store user info in it after successful login

        String incomingUsername = incomingUser.getUsername();
        String incomingEmail = incomingUser.getEmail();
        String incomingPassword = incomingUser.getPassword();

        User loggedInUser = new User(incomingUsername, incomingEmail, incomingPassword, "user");

        if (!users.containsKey(incomingUsername)) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }

        if (!users.get(incomingUsername).equals(incomingPassword)) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }
        
        if (users.containsKey(incomingUsername) && users.get(incomingUsername).equals(incomingPassword)) {

            //Store the user info in the session
            session.setAttribute("username", incomingUsername);
            session.setAttribute("email", incomingEmail);
            session.setAttribute("role", "user");
            //Note: we didn't save the password in the session. No reason to!

            //It's really easy to extract this session info
            System.out.println("Session ID: " + session.getId());
            System.out.println("Session Username: " + session.getAttribute("username"));

            //Return the user info to the client (the frontend)
            return ResponseEntity.ok().body(loggedInUser);

        } else {
            //If login fails, we can throw a 401 (unauthorized) and an error message
            return ResponseEntity.status(401).body("Invalid username or password");
        }

    }

}
