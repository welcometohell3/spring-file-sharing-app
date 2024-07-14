package com.welcometohell.filesharing.controller;

import com.welcometohell.filesharing.entity.User;
import com.welcometohell.filesharing.repo.UserRepository;
import com.welcometohell.filesharing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MainController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/welcome")
    public String welcome(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        User user = userRepository.findUserByName(currentUsername).orElse(null);
        if (user != null) {
            return "Welcome to the unprotected page, " + user.getName();
        } else {
            return "User not found";
        }
    }

    @GetMapping("/users")
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
//
//    @PostMapping("/do")
//    public User doSome(@RequestBody User user){
//       return userRepository.save(user);
//    }

    @PostMapping("/new-user")
    public String addUser(@RequestBody User user) {
        userService.addUser(user);
        return "User is saved";
    }
}
