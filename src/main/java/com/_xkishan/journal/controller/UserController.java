package com._xkishan.journal.controller;

import com._xkishan.journal.entity.UserEntry;
import com._xkishan.journal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import static com._xkishan.journal.utils.Utils.override;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody UserEntry userEntry) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        UserEntry found = userService.findByUserName(username);

        found.setUserName(override(username, userEntry.getUserName()));
        found.setPassword(override(found.getPassword(), userEntry.getPassword()));
        // overwrite
        userService.save(found);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @DeleteMapping
    public ResponseEntity<?> deleteUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        userService.deleteByUserName(username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
