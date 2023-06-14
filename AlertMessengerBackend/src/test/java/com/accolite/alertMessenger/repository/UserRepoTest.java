package com.accolite.alertMessenger.repository;

import com.accolite.alertMessenger.model.Message;
import com.accolite.alertMessenger.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest(properties = {
        "spring.test.database.replace=NONE",
        "spring.datasource.url=jdbc:mysql://localhost:3306/alertmessenger"
})
class UserRepoTest {
    @Autowired
    private UserRepo userRepo;

    BCryptPasswordEncoder bCryptPasswordEncoder =
            new BCryptPasswordEncoder();

    @Test
    void whenGivenCorrectUserId_thenShouldReturnCorrectUser() {

        String userId = "admin";

        User user1 = User.builder()
                .userId("admin")
                .password(bCryptPasswordEncoder.encode("1234"))
                .role("ADMIN")
                .build();

        userRepo.save(user1);

        User user2 = userRepo.findByUserId(userId);

        assertEquals(user1, user2);
    }
}