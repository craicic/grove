package org.motoc.gamelibrary.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController() {
    }

    @GetMapping("/login")
    Principal login(Principal user) {
        logger.info(user.getName());
        return user;
    }

    @PostMapping("/login")
    void logout() {
        logger.info("Disconnected");
    }
}
