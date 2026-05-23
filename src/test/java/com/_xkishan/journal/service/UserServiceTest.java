package com._xkishan.journal.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTest {

//    @Autowired
//    private UserRepository userRepository;

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
          "1,1,2application-dev.properties",
          "2,10,12"
      }
  )
  public void test(int a, int b, int expected) {
    Assertions.assertEquals(expected, a + b);
  }
}
