package com._xkishan.journal.controller;

import com._xkishan.journal.entity.UserEntry;
import com._xkishan.journal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com._xkishan.journal.utils.Utils.override;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserEntry>> getAllUsers() {
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserEntry> createUser(@RequestBody UserEntry userEntry) {
        return new ResponseEntity<>(userService.save(userEntry), HttpStatus.CREATED);
    }

    @PutMapping("/{username}")
    public ResponseEntity<?> updateUser(@PathVariable String username,
                                        @RequestBody UserEntry userEntry) {
        UserEntry found = userService.findByUserName(username);
        if (found != null) {
            found.setUserName(override(username, userEntry.getUserName()));
            found.setPassword(override(found.getPassword(), userEntry.getPassword()));
            // overwrite
            userService.save(found);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
