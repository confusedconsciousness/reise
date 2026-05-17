package com._xkishan.journal.service;

import com._xkishan.journal.entity.UserEntry;
import com._xkishan.journal.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserEntry save(UserEntry userEntry) {
        return userRepository.save(userEntry);
    }

    public List<UserEntry> getAll() {
        return userRepository.findAll();
    }

    public Optional<UserEntry> findById(ObjectId id) {
        return userRepository.findById(id);
    }

    public void deleteById(ObjectId id) {
        userRepository.deleteById(id);
    }

    public UserEntry findByUserName(String username) {
        return userRepository.findUserEntryByUserName(username);
    }

}
