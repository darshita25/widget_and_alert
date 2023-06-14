package com.accolite.alertMessenger.service;

import com.accolite.alertMessenger.exception.MessageNotFoundException;
import com.accolite.alertMessenger.model.Message;
import com.accolite.alertMessenger.repository.MessageRepo;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MessageServiceTest {
    @MockBean
    private MessageRepo messageRepo;

    @BeforeEach
     void setUp() {
        UUID id1 = UUID.fromString("ac696dd8-51f1-4c9b-b7a2-27dff80ad5b7");

        Message message = Message
                .builder()
                .messageId(id1)
                .flight("INDIGO")
                .desk("DESK")
                .acknowledge("NO")
                .acknowledgedBy("MRIDUL")
                .aircraftRegistration("UK-07")
                .deskCategory("PILOT")
                .isPublished(1)
                .escalated("NO")
                .priority("HIGH")
                .received("YASH")
                .build();

        UUID id2 = UUID.fromString("d2485c29-b3c1-46b4-8554-303986a9bc3e");
        Message readMessage = Message
                .builder()
                .messageId(id2)
                .desk("DESK")
                .flight("QATAR-AIRWAYS")
                .acknowledge("YES")
                .acknowledgedBy("MRIDUL")
                .aircraftRegistration("UK-07")
                .deskCategory("PILOT")
                .isPublished(1)
                .escalated("NO")
                .priority("HIGH")
                .received("YASH")
                .build();

        UUID id3 = UUID.fromString("61ad0949-9d9c-4af7-b99c-29b45cd3ac29");
        Message unreadMessage = Message
                .builder()
                .messageId(id3)
                .desk("DESK")
                .acknowledge("NO")
                .flight("AIR-INDIA")
                .acknowledgedBy("MRIDUL")
                .aircraftRegistration("UK-07")
                .deskCategory("PILOT")
                .isPublished(1)
                .escalated("NO")
                .priority("HIGH")
                .received("YASH")
                .build();

        List<Message> unreadMessageList = new ArrayList<>();
        unreadMessageList.add(unreadMessage);

        List<Message> readMessageList = new ArrayList<>();
        readMessageList.add(readMessage);

        List<Message> messageList = new ArrayList<>();
        messageList.add(message);

        List<Message> publishedMessage = new ArrayList<>();
        publishedMessage.add(readMessage);
        publishedMessage.add(unreadMessage);
        publishedMessage.add(message);

        when(messageRepo.findById(id1))
                .thenReturn(Optional.of(message));

        when(messageRepo.findAll())
                .thenReturn(messageList);

        Mockito.when(messageRepo.save(message))
                .thenReturn(message);

        Mockito.when(messageRepo.getUnreadMessagesForUser())
                .thenReturn(unreadMessageList);

        Mockito.when(messageRepo.getReadMessagesForUser())
                .thenReturn(readMessageList);

        Mockito.when(messageRepo.getPublishedData())
                .thenReturn(publishedMessage);

    }

    @Autowired
    private MessageService messageService;

    @Test
    @DisplayName("Get Valid Message Based On the Message ID")
    public void whenValidMessageId_ThenReturnValidMessage() throws MessageNotFoundException {

        UUID messageId = UUID.fromString("ac696dd8-51f1-4c9b-b7a2-27dff80ad5b7");

       Message message = messageService.getDataById(messageId);

        assertEquals(messageId, message.getMessageId());

    }

    @Test
    @DisplayName("When we save data then it should be properly saved")
    public void whenSavingData_ThenSaveProperly(){

        UUID id1 = UUID.fromString("ac696dd8-51f1-4c9b-b7a2-27dff80ad5b7");
        Message message = Message
                .builder()
                .messageId(id1)
                .flight("INDIGO")
                .desk("DESK")
                .acknowledge("NO")
                .acknowledgedBy("MRIDUL")
                .aircraftRegistration("UK-07")
                .deskCategory("PILOT")
                .isPublished(1)
                .escalated("NO")
                .priority("HIGH")
                .received("YASH")
                .build();
        Message savedData = messageService.saveData(message);

        assertEquals(message, savedData);

    }

    @Test
    @DisplayName("All the data should be fetched correctly")
    public void whenFetchingAllData_ThenReturnAllData(){

        List<Message> fetchedMessages  = messageService.getData();
        assertEquals(fetchedMessages.size(), 1);

    }

    @Test
    public void whenCorrectMessageIdGiven_ThenDeleteTheMessage(){
        UUID messageId = UUID.fromString("ac696dd8-51f1-4c9b-b7a2-27dff80ad5b7");
        messageService.deleteData(messageId);
        Mockito.verify(messageRepo).deleteById(messageId);
    }

    @Test
    public void whenUpdateData_ThenTheDataShouldBeUpdated(){
        UUID messageId = UUID.fromString("ac696dd8-51f1-4c9b-b7a2-27dff80ad5b7");

        Message messageToBeUpdated = Message
                .builder()
                .messageId(messageId)
                .flight("INDIGO")
                .desk("DESK")
                .acknowledge("NO")
                .acknowledgedBy("MRIDUL")
                .aircraftRegistration("UK-07")
                .deskCategory("PILOT")
                .isPublished(1)
                .escalated("NO")
                .priority("HIGH")
                .received("YASH")
                .build();

        Message updatedMessage = messageService.updateData(messageToBeUpdated, messageId);
        assertEquals(messageToBeUpdated, updatedMessage);
    }


    @Test
    public void whenPublishData_ThenMessageShouldBePublished() throws MessageNotFoundException {

        UUID messageId = UUID.fromString("ac696dd8-51f1-4c9b-b7a2-27dff80ad5b7");

        Message messageToBePublished = Message
                .builder()
                .messageId(messageId)
                .flight("INDIGO")
                .desk("DESK")
                .acknowledge("NO")
                .acknowledgedBy("MRIDUL")
                .aircraftRegistration("UK-07")
                .deskCategory("PILOT")
                .isPublished(1)
                .escalated("NO")
                .priority("HIGH")
                .received("YASH")
                .build();

        Message publishedMessage = messageService.publishData(messageToBePublished, messageId);

        assertEquals(publishedMessage, messageToBePublished);
    }

    @Test
    public void whenDataIsAcknowledged_ThenDataShouldBeAcknowledged() throws MessageNotFoundException {

        UUID messageId = UUID.fromString("ac696dd8-51f1-4c9b-b7a2-27dff80ad5b7");

        Message messageToBeAcknowledged = Message
                .builder()
                .messageId(messageId)
                .flight("INDIGO")
                .desk("DESK")
                .acknowledge("NO")
                .acknowledgedBy("MRIDUL")
                .aircraftRegistration("UK-07")
                .deskCategory("PILOT")
                .isPublished(1)
                .escalated("NO")
                .priority("HIGH")
                .received("YASH")
                .build();

        Message acknowledgedMessage = messageService.acknowledgeData(messageToBeAcknowledged, messageId);
        assertEquals(acknowledgedMessage.getAcknowledge(), "YES");

    }

    @Test
    public void whenFetchingUnreadMessages_ThenOnlyUnreadMessagesShouldBeReturned(){
        List<Message> unreadMessageList = messageService.getUnreadDataForUser();
        assertEquals(unreadMessageList.size(), 1);
    }

    @Test
    public void whenFetchingReadMessages_ThenOnlyReadMessagesShouldBeReturned(){
        List<Message> readMessageList = messageService.getReadDataForUser();
        assertEquals(readMessageList.size(), 1);
    }

    @Test
    public void whenFetchingPublishedMessages_ThenOnlyPublishedMessagesShouldBeReturned(){
        List<Message> publishedMessageList = messageService.getPublishedData();
        assertEquals(publishedMessageList.size(), 3);
    }

    @Test
    public void whenInvalidMessageId_ThenThrowExceptionForFetchingDataById() throws MessageNotFoundException {
        assertThrows(MessageNotFoundException.class, ()->{
            UUID messageId = UUID.fromString("ac696dd8-51f1-9c9b-b7a2-27dff80ad5b7");
            Message message = messageService.getDataById(messageId);
        });
    }

    @Test
    public void whenInvalidMessageId_ThenThrowExceptionForDeleteData() throws Exception {
        assertThrows(Exception.class, ()->{
            UUID messageId = UUID.fromString("ac6sd96dd8-51f1-9c9b-b7a2-27dff80ad5b7");
            messageService.deleteData(messageId);
        });
    }

    @Test
    public void whenInvalidMessageId_ThenThrowExceptionForUpdateData() throws Exception {
        assertThrows(Exception.class, ()->{
            UUID messageId = UUID.fromString("ac6sd96dd8-51f1-9c9b-b7a2-27dff80ad5b7");
            UUID id1 = UUID.fromString("ac696dd8-51f1-4c9b-b7a2-27dff80ad5b7");

            Message message = Message
                    .builder()
                    .messageId(id1)
                    .flight("INDIGO")
                    .desk("DESK")
                    .acknowledge("NO")
                    .acknowledgedBy("MRIDUL")
                    .aircraftRegistration("UK-07")
                    .deskCategory("PILOT")
                    .isPublished(1)
                    .escalated("NO")
                    .priority("HIGH")
                    .received("YASH")
                    .build();

            messageService.updateData(message, messageId);
        });
    }

    @Test
    public void whenInvalidMessage_ThenThrowExceptionForSaveData() throws Exception {
        assertThrows(Exception.class, ()->{
            UUID messageId = UUID.fromString("ac6sd96dd8-51f1-9c9b-b7a2-27dff80ad5b7");

            Message message = Message
                    .builder()
                    .messageId(messageId)
                    .desk("DESK")
                    .acknowledge("NO")
                    .acknowledgedBy("MRIDUL")
                    .aircraftRegistration("UK-07")
                    .deskCategory("PILOT")
                    .isPublished(1)
                    .escalated("NO")
                    .priority("HIGH")
                    .received("YASH")
                    .build();

            messageService.saveData(message);
        });
    }

    @Test
    public void whenInvalidMessage_ThenThrowExceptionForPublishingData() throws Exception {
        assertThrows(Exception.class, ()->{
            UUID messageId = UUID.fromString("ac6sd96dd8-51f1-9c9b-b7a2-27dff80ad5b7");

            Message message = Message
                    .builder()
                    .messageId(messageId)
                    .desk("DESK")
                    .acknowledge("NO")
                    .acknowledgedBy("MRIDUL")
                    .aircraftRegistration("UK-07")
                    .deskCategory("PILOT")
                    .isPublished(1)
                    .escalated("NO")
                    .priority("HIGH")
                    .received("YASH")
                    .build();

            messageService.publishData(message, messageId);
        });
    }

    @Test
    public void whenInvalidMessage_ThenThrowExceptionForAcknowledgingData() throws Exception {
        assertThrows(Exception.class, ()->{
            UUID messageId = UUID.fromString("ac6sd96dd8-51f1-9c9b-b7a2-27dff80ad5b7");

            Message message = Message
                    .builder()
                    .messageId(messageId)
                    .desk("DESK")
                    .acknowledge("NO")
                    .acknowledgedBy("MRIDUL")
                    .aircraftRegistration("UK-07")
                    .deskCategory("PILOT")
                    .isPublished(1)
                    .escalated("NO")
                    .priority("HIGH")
                    .received("YASH")
                    .build();

            messageService.acknowledgeData(message, messageId);
        });
    }

}