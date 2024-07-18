package com.welcometohell.filesharing.runner;

import com.welcometohell.filesharing.entity.User;
import com.welcometohell.filesharing.security.WebSecurityConfig;
import com.welcometohell.filesharing.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class DBInitializer implements CommandLineRunner {
    private final UserService userService;

    @Override
    public void run(String... args) throws Exception {
        if (!userService.getUsers().isEmpty()) {
            return;
        }
        USERS.forEach(userService::saveUser);
        log.info("Database initialized");
    }
    private static final List<User> USERS = Arrays.asList(
            new User("admin", "admin", "Admin", "admin@mycompany.com", WebSecurityConfig.ADMIN),
            new User("user", "user", "User", "user@mycompany.com", WebSecurityConfig.USER)
    );

}

