package com._xkishan.journal.service;

import com._xkishan.journal.entity.JournalEntry;
import com._xkishan.journal.entity.UserEntry;
import com._xkishan.journal.repository.JournalRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalService {

    @Autowired
    private JournalRepository journalRepository;
    @Autowired
    private UserService userService;

    public JournalEntry createJournal(JournalEntry journalEntry,
                                      String username) {
        UserEntry user = userService.findByUserName(username);

        journalEntry.setDate(LocalDateTime.now());
        JournalEntry saved = journalRepository.save(journalEntry);

        user.getJournalEntries().add(saved);
        userService.save(user);
        return saved;
    }

    public JournalEntry updateJournal(JournalEntry updatedEntry) {
        return journalRepository.save(updatedEntry);
    }

    public List<JournalEntry> getAll() {
        return journalRepository.findAll();
    }

    public Optional<JournalEntry> findJournalById(ObjectId id) {
        return journalRepository.findById(id);
    }

    public void deleteJournalById(String username,
                                  ObjectId id) {
        UserEntry user = userService.findByUserName(username);
        user.getJournalEntries().removeIf(k -> k.getId().equals(id));
        userService.save(user);
        journalRepository.deleteById(id);
    }

}
