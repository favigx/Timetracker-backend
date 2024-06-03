package com.timetracker.timetracker.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.timetracker.timetracker.models.User;
import com.timetracker.timetracker.services.UserService;

@CrossOrigin(origins = "*")
@RestController
public class UserController {

    public UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/user/{username}")
    public User getUserById(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }

    @PostMapping("/user")
    public ResponseEntity<?> addUser(@RequestBody User user) {
        try {
            User addedUser = userService.addUser(user);
            return ResponseEntity.ok(addedUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Det uppstod ett fel vid försök att lägga till användaren.");
        }
    }

    @DeleteMapping("/user/{id}")
    public String deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return "{'message': 'User with id " + id + "has been deleted.'}";

    }
}