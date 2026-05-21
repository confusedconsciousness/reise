package com._xkishan.journal.controller;


import com._xkishan.journal.entity.JournalEntry;
import com._xkishan.journal.entity.UserEntry;
import com._xkishan.journal.service.JournalService;
import com._xkishan.journal.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com._xkishan.journal.utils.Utils.override;

@RestController
@RequestMapping("/journals") // sort of adds prefix
public class JournalController {

    @Autowired
    private JournalService journalService;
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<JournalEntry>> getAllJournalEntriesOfUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserEntry user = userService.findByUserName(username);
        List<JournalEntry> all = user.getJournalEntries();

        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createJournalForUser(@RequestBody JournalEntry entry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        try {
            return new ResponseEntity<>(journalService.createJournal(entry, username), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("id/{id}")
    public ResponseEntity<JournalEntry> getJournalById(@PathVariable ObjectId id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserEntry user = userService.findByUserName(username);
        Optional<JournalEntry> journalEntry = user.getJournalEntries().stream().filter(x -> x.getId().equals(id))
                                                  .findFirst();

        return journalEntry.map(entry -> new ResponseEntity<>(entry, HttpStatus.OK))
                           .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("id/{id}")
    public ResponseEntity<?> deleteJournalById(@PathVariable ObjectId id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            journalService.deleteJournalById(username, id);
            return new ResponseEntity<>(true, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }


    }

    @PutMapping("id/{id}")
    public ResponseEntity<JournalEntry> updateJournalById(@PathVariable ObjectId id,
                                                          @RequestBody JournalEntry newEntry) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntry user = userService.findByUserName(username);
        Optional<JournalEntry> found = user.getJournalEntries().stream().filter(x -> x.getId().equals(id)).findFirst();
        if (found.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        // username is needed when we add authn
        JournalEntry old = journalService.findJournalById(id).orElse(null);
        if (old != null) {
            old.setTitle(override(old.getTitle(), newEntry.getTitle()));
            old.setContent(override(old.getContent(), newEntry.getContent()));

            return new ResponseEntity<>(journalService.updateJournal(old), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}

