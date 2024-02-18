package com.Wishlist.Management.controllers;

import com.Wishlist.Management.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> addUser(@RequestParam String username, @RequestParam String password) {
        // Call the userService to add a new user with the provided username and password
        String result=  userService.addUser(username, password);
         return ResponseEntity.ok(result);
    }
}
