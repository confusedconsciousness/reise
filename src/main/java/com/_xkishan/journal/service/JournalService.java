package com._xkishan.journal.service;

import com._xkishan.journal.entity.JournalEntry;
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

    public JournalEntry createJournal(JournalEntry journalEntry) {
        journalEntry.setDate(LocalDateTime.now());
        return journalRepository.save(journalEntry);
    }

    public List<JournalEntry> getAll() {
        return journalRepository.findAll();
    }

    public Optional<JournalEntry> findJournalById(ObjectId id) {
        return journalRepository.findById(id);
    }

    public void deleteJournalById(ObjectId id) {
        journalRepository.deleteById(id);
    }

}
