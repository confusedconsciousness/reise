package com._xkishan.journal.service;

import com._xkishan.journal.entity.UserEntry;
import com._xkishan.journal.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserEntry saveNew(UserEntry userEntry) {
        userEntry.setPassword(Objects.requireNonNull(passwordEncoder.encode(userEntry.getPassword())));
        userEntry.setRoles(List.of("USER"));
        return userRepository.save(userEntry);
    }

    public UserEntry save(UserEntry userEntry) {
        return userRepository.save(userEntry);
    }

    public void deleteByUserName(String username) {
        userRepository.deleteByUserName(username);
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
