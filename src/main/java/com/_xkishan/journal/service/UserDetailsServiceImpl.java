package com._xkishan.journal.service;

import com._xkishan.journal.entity.UserEntry;
import com._xkishan.journal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws
            UsernameNotFoundException {
        UserEntry user = userRepository.findUserEntryByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return User.withUsername(user.getUserName()).password(user.getPassword())
                   .roles(user.getRoles().toArray(new String[0])).build();
    }
}
