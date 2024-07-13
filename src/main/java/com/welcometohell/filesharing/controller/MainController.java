package com.welcometohell.filesharing.controller;

import com.welcometohell.filesharing.entity.User;
import com.welcometohell.filesharing.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MainController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @PostMapping("/do")
    public User doSome(@RequestBody User user){
       return userRepository.save(user);
    }
}
