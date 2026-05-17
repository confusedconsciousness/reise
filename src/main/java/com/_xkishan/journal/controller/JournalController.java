package com._xkishan.journal.controller;


import com._xkishan.journal.entity.JournalEntry;
import com._xkishan.journal.entity.UserEntry;
import com._xkishan.journal.service.JournalService;
import com._xkishan.journal.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("{username}")
    public ResponseEntity<List<JournalEntry>> getAllJournalEntriesOfUser(@PathVariable String username) {
        UserEntry user = userService.findByUserName(username);
        List<JournalEntry> all = user.getJournalEntries();

        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @PostMapping("{username}")
    public ResponseEntity<JournalEntry> createJournalForUser(@PathVariable String username,
                                                             @RequestBody JournalEntry entry) {
        try {
            return new ResponseEntity<>(journalService.createJournal(entry, username), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("id/{id}")
    public ResponseEntity<JournalEntry> getJournalById(@PathVariable ObjectId id) {
        Optional<JournalEntry> journalEntry = journalService.findJournalById(id);
        return journalEntry.map(entry -> new ResponseEntity<>(entry, HttpStatus.OK))
                           .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("id/{username}/{id}")
    public ResponseEntity<?> deleteJournalById(@PathVariable String username,
                                               @PathVariable ObjectId id) {
        try {
            journalService.deleteJournalById(username, id);
            return new ResponseEntity<>(true, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }


    }

    @PutMapping("/{username}/{id}")
    public ResponseEntity<JournalEntry> updateJournalById(@PathVariable String username,
                                                          @PathVariable ObjectId id,
                                                          @RequestBody JournalEntry newEntry) {
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

