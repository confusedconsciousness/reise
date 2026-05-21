package com._xkishan.journal.service;

import com._xkishan.journal.entity.JournalEntry;
import com._xkishan.journal.entity.UserEntry;
import com._xkishan.journal.repository.JournalRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class JournalService {

    @Autowired
    private JournalRepository journalRepository;
    @Autowired
    private UserService userService;

    @Transactional
    public JournalEntry createJournal(JournalEntry journalEntry,
                                      String username) {

        try {
            UserEntry user = userService.findByUserName(username);

            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalRepository.save(journalEntry);

            user.getJournalEntries().add(saved);
            userService.save(user);
            return saved;
        } catch (Exception e) {
            log.info("An error occurred while saving the journal ", e);
            throw e;
        }

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

    @Transactional
    public void deleteJournalById(String username,
                                  ObjectId id) {
        try {
            UserEntry user = userService.findByUserName(username);
            boolean removed = user.getJournalEntries().removeIf(k -> k.getId().equals(id));
            if (removed) {
                userService.save(user);
                journalRepository.deleteById(id);
            }
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while deleting the journal entry, ", e);
        }

    }

}
