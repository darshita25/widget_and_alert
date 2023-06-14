package com.accolite.alertMessenger.controller;

import com.accolite.alertMessenger.model.Message;
import com.accolite.alertMessenger.model.User;
import com.accolite.alertMessenger.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/accolite/alertmessenger")
public class UserController {


    @Autowired
    private UserService userService;

    @PostMapping("/addUser")
    public User addUser(@RequestBody @Valid User user){
        return userService.addUser(user);
    }

    @PutMapping("/login")
    public ResponseEntity<User> login(@RequestBody  User user) throws Exception {
        return new ResponseEntity<User>(userService.login(user), HttpStatus.ACCEPTED);
    }

    @GetMapping("/fetchUser")
    public List<User> getUser(){
        return userService.getUser();
    }

}
