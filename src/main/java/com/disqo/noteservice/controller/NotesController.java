package com.disqo.noteservice.controller;

import com.disqo.noteservice.repositories.UserRepository;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class to handle CRUD operations on Notes.
 * @author Haridas Parekh
 */
@RestController
@RequestMapping("/api/v1.0")
@CommonsLog
public class NotesController {

    @Autowired
    UserRepository userRepository;

    @RequestMapping({"/testAuth"})
    public String testAuth(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        log.info("Current logged in User : " + user.toString());
        log.info("User Found from the database : " + userRepository.findByEmail(user.getUsername()).toString());
        return "Hello note-service";
    }
}
