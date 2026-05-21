package com._xkishan.journal.controller;

import com._xkishan.journal.entity.UserEntry;
import com._xkishan.journal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("all-users")
    public ResponseEntity<List<UserEntry>> getAllUsers() {
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);

    }
}
