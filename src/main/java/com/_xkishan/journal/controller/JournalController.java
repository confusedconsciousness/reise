package com._xkishan.journal.controller;


import com._xkishan.journal.entity.JournalEntry;
import com._xkishan.journal.service.JournalService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journals") // sort of adds prefix
public class JournalController {

    @Autowired
    private JournalService journalService;

    @GetMapping
    public ResponseEntity<List<JournalEntry>> getAll() {
        List<JournalEntry> all = journalService.getAll();
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry entry) {
        try {
            return new ResponseEntity<>(journalService.createJournal(entry), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<JournalEntry> getJournalById(@PathVariable ObjectId id) {
        Optional<JournalEntry> journalEntry = journalService.findJournalById(id);
        return journalEntry.map(entry -> new ResponseEntity<>(entry, HttpStatus.OK))
                           .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteJournalById(@PathVariable ObjectId id) {
        try {
            journalService.deleteJournalById(id);
            return new ResponseEntity<>(true, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }


    }

    @PutMapping("/{id}")
    public ResponseEntity<JournalEntry> updateJournalById(@PathVariable ObjectId id,
                                                          @RequestBody JournalEntry newEntry) {
        JournalEntry old = journalService.findJournalById(id).orElse(null);
        if (old != null) {
            old.setTitle(override(old.getTitle(), newEntry.getTitle()));
            old.setContent(override(old.getContent(), newEntry.getContent()));

            return new ResponseEntity<>(journalService.createJournal(old), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    private String override(String oldContent,
                            String newContent) {
        return (newContent == null || newContent.isEmpty()) ? oldContent : newContent;
    }

}

