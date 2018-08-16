package com.gubba.library.api.controller;

import com.gubba.library.api.model.User;
import com.gubba.library.api.service.LibraryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private LibraryService libraryService;

    @PostMapping(value = "/addUser")
    public ResponseEntity addUser(@RequestBody User user) {
        User createdUser = libraryService.addUser(user);
        return ResponseEntity.ok("User successfully created with id " + createdUser.getId());
    }

    @PostMapping(value = "/addUsers")
    public ResponseEntity addUser(@RequestBody List<User> users) {
        libraryService.addUsers(users);
        return ResponseEntity.ok("User successfully added");
    }

    @GetMapping(value = "/getAllUsers")
    public ResponseEntity getAllUsers() {
        return ResponseEntity.ok(libraryService.getAllRegisteredUsers());
    }
}
