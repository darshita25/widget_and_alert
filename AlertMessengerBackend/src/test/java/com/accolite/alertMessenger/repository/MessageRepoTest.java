package com.accolite.alertMessenger.repository;

import com.accolite.alertMessenger.model.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest(properties = {
        "spring.test.database.replace=NONE",
        "spring.datasource.url=jdbc:mysql://localhost:3306/alertmessenger"
})
class MessageRepoTest {
    @Autowired
    private MessageRepo messageRepo;

    UUID id1 = UUID.fromString("ac696dd8-51f1-4c9b-b7a2-27dff80ad5b7");
    UUID id2 = UUID.fromString("d2485c29-b3c1-46b4-8554-303986a9bc3e");
    UUID id3 = UUID.fromString("61ad0949-9d9c-4af7-b99c-29b45cd3ac29");

    List<Message> unreadMessageList = new ArrayList<>();
    List<Message> readMessageList = new ArrayList<>();
    List<Message> publishedMessageList = new ArrayList<>();

    @BeforeEach
    void setUp() {

        Message message = Message
                .builder()
                .messageId(id1)
                .desk("DESK")
                .acknowledge("NO")
                .acknowledgedBy("MRIDUL")
                .aircraftRegistration("UK-07")
                .deskCategory("PILOT")
                .isPublished(1)
                .flight("INDIGO")
                .escalated("NO")
                .priority("HIGH")
                .received("YASH")
                .build();

        Message readMessage = Message
                .builder()
                .messageId(id2)
                .desk("DESK")
                .acknowledge("YES")
                .acknowledgedBy("MRIDUL")
                .flight("QATAR-AIRWAYS")
                .aircraftRegistration("UK-07")
                .deskCategory("PILOT")
                .isPublished(1)
                .escalated("NO")
                .priority("HIGH")
                .received("YASH")
                .build();

        Message unreadMessage = Message
                .builder()
                .messageId(id3)
                .desk("DESK")
                .acknowledge("NO")
                .acknowledgedBy("MRIDUL")
                .aircraftRegistration("UK-07")
                .deskCategory("PILOT")
                .flight("AIR-INDIA")
                .isPublished(1)
                .escalated("NO")
                .priority("HIGH")
                .received("YASH")
                .build();

        unreadMessageList.add(unreadMessage);
        readMessageList.add(readMessage);
        publishedMessageList.add(readMessage);
        publishedMessageList.add(unreadMessage);
        publishedMessageList.add(message);

        messageRepo.save(unreadMessage);
        messageRepo.save(readMessage);
        messageRepo.save(message);
    }

    @Test
    void whenFetchingUnreadMessages_thenShouldGetCorrectUnreadMessagesForUser() {

        List<Message> resultMessageList = messageRepo.getUnreadMessagesForUser();
        assertEquals(2, resultMessageList.size());

    }

    @Test
    void  whenFetchingReadMessages_thenShouldGetCorrectReadMessagesForUser() {

        List<Message> resultMessageList = messageRepo.getReadMessagesForUser();

        assertEquals(1, resultMessageList.size());
    }

    @Test
    void  whenFetchingPublishedMessages_thenShouldGetPublishedData() {

        List<Message> resultMessageList = messageRepo.getPublishedData();
        assertEquals(3, resultMessageList.size());
    }

}