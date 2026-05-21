package com._xkishan.journal.controller;

import com._xkishan.journal.entity.UserEntry;
import com._xkishan.journal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;


    @PostMapping("create-user")
    public ResponseEntity<UserEntry> createUser(@RequestBody UserEntry userEntry) {
        return new ResponseEntity<>(userService.saveNew(userEntry), HttpStatus.CREATED);
    }

}
