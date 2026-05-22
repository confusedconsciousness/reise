package com._xkishan.journal.service;

import static org.mockito.Mockito.when;

import com._xkishan.journal.entity.UserEntry;
import com._xkishan.journal.repository.UserRepository;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;


public class UserDetailsServiceImplTest {

  @InjectMocks
  private UserDetailsServiceImpl userDetailsService;

  @Mock
  private UserRepository userRepository;

  @BeforeEach
  void setup() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void loadUserByUsernameTest() {

    when(userRepository
        .findUserEntryByUserName(ArgumentMatchers.anyString()))
        .thenReturn(
            UserEntry.builder()
                .userName("ram")
                .password("abc")
                .roles(List.of())
                .build()
        );
    UserDetails user = userDetailsService.loadUserByUsername("ram");
    Assertions.assertEquals("ram", user.getUsername());
  }
}
