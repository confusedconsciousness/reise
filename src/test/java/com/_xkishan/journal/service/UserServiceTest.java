package com._xkishan.journal.service;

import com._xkishan.journal.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserRepository userRepository;
    @ParameterizedTest
    @ValueSource(strings = {
            "ram",
            "shyam"
    })
    public void testAdd(String name) {

    }

    @ParameterizedTest
    @CsvSource(
            {
                    "1,1,2",
                    "2,10,12"
            }
    )
    public void test(int a, int b, int expected) {
        Assertions.assertEquals(expected, a + b);
    }
}
