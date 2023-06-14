package com.accolite.alertMessenger.controller;

import com.accolite.alertMessenger.exception.MessageNotFoundException;
import com.accolite.alertMessenger.model.Message;
import com.accolite.alertMessenger.repository.MessageRepo;
import com.accolite.alertMessenger.service.MessageService;
import com.accolite.alertMessenger.service.implementation.MessageServiceImplementation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.objenesis.instantiator.util.UnsafeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.RequestEntity.post;
import static org.springframework.http.RequestEntity.put;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.web.servlet.function.RequestPredicates.contentType;


@WebMvcTest(controllers = MessageController.class)
class MessageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MessageService messageService;

    private Message message;
    private Message updatedMessage;
    private Message acknowledgedMessage;
    private Message publishedMessage;
    private List<Message> messageList = new ArrayList<>();
    private List<Message> unreadMessageList = new ArrayList<>();
    private List<Message> readMessageList = new ArrayList<>();
    private List<Message> publishedMessageList = new ArrayList<>();
    private List<Message> emptyMessageList = new ArrayList<>();


    @Autowired
    private WebApplicationContext context;

    //It is used to read and write JSON data
    //rawObject -> JSON
    //JSON -> Object
    @Autowired
    private ObjectMapper objectMapper;


    @BeforeEach
    void setUp() {
        UUID id1 = UUID.fromString("ac696dd8-51f1-4c9b-b7a2-27dff80ad5b7");

        message = Message
                .builder()
                .flight("AIR-INDIA")
                .messageId(id1)
                .desk("DESK")
                .acknowledge("NO")
                .acknowledgedBy("MRIDUL")
                .aircraftRegistration("UK-07")
                .deskCategory("PILOT")
                .isPublished(0)
                .escalated("NO")
                .priority("HIGH")
                .received("YASH")
                .build();

        acknowledgedMessage = Message
                .builder()
                .flight("AIR-INDIA")
                .messageId(id1)
                .desk("DESK")
                .acknowledge("YES")
                .acknowledgedBy("MRIDUL")
                .aircraftRegistration("UK-07")
                .deskCategory("PILOT")
                .isPublished(0)
                .escalated("NO")
                .priority("HIGH")
                .received("YASH")
                .build();

        publishedMessage =  Message
                .builder()
                .flight("AIR-INDIA")
                .messageId(id1)
                .desk("DESK")
                .acknowledge("YES")
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
                .acknowledgedBy("MRIDUL")
                .aircraftRegistration("UK-07")
                .deskCategory("PILOT")
                .isPublished(1)
                .escalated("NO")
                .priority("HIGH")
                .received("YASH")
                .build();

        messageList.add(message);
        unreadMessageList.add(unreadMessage);
        readMessageList.add(readMessage);
        publishedMessageList.add(unreadMessage);
        publishedMessageList.add(readMessage);

        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        updatedMessage = Message
                .builder()
                .flight("AIR-INDIA")
                .messageId(id1)
                .desk("DESK")
                .acknowledge("NO")
                .acknowledgedBy("MRIDUL SEMWAL")
                .aircraftRegistration("UK-07")
                .deskCategory("PILOT")
                .isPublished(0)
                .escalated("NO")
                .priority("HIGH")
                .received("YASH")
                .build();

    }

    @Test
    public void saveDataTest() throws Exception {

        Message inputMessage = Message
                .builder()
                .desk("DESK")
                .flight("AIR_INDIA")
                .acknowledge("NO")
                .acknowledgedBy("MRIDUL")
                .aircraftRegistration("UK-07")
                .deskCategory("PILOT")
                .isPublished(0)
                .escalated("NO")
                .priority("HIGH")
                .received("YASH")
                .build();

        String jsonRequest = objectMapper.writeValueAsString(message);

        Mockito.when(messageService.saveData(inputMessage))
                .thenReturn(message);

        mockMvc.perform(MockMvcRequestBuilders.post("/accolite/alertmessenger/saveData")
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void saveDataTestShouldThroughException_WhenGivenInvalidMessage() throws Exception {

        Message inputMessage = Message
                .builder()
                .desk("DESK")
                .flight("AIR_INDIA")
                .acknowledge("NO")
                .acknowledgedBy("MRIDUL")
                .deskCategory("PILOT")
                .build();

        String jsonRequest = objectMapper.writeValueAsString(inputMessage);

        mockMvc.perform(MockMvcRequestBuilders.post("/accolite/alertmessenger/saveData")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("When fetching data then it should fetch all data correctly with the correct status code")
    public void fetchDataTest() throws Exception {

        Mockito.when(messageService.getData())
                .thenReturn(messageList);

        mockMvc.perform(MockMvcRequestBuilders.get("/accolite/alertmessenger/fetchData"))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void fetchDataByIdTest() throws Exception{
        UUID messageId = UUID.fromString("ac696dd8-51f1-4c9b-b7a2-27dff80ad5b7");

        Mockito.when(messageService.getDataById(messageId))
                .thenReturn(message);


        mockMvc.perform(MockMvcRequestBuilders.get("/accolite/alertmessenger/getbyid/ac696dd8-51f1-4c9b-b7a2-27dff80ad5b7")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("When fetching data then it should fetch all data correctly with the correct status code")
    public void fetchDataByIdTestShouldThroughException() throws Exception {

        UUID messageId = UUID.fromString("ac789ee9-51f1-4c9b-b7a2-27dff80ad5b7");

        Mockito.when(messageService.getDataById(messageId))
                .thenThrow(MessageNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/accolite/alertmessenger/fetchData/ac789ee9-51f1-4c9b-b7a2-27dff80ad5b7"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }

    @Test
    public void updateDataTest() throws Exception{

        UUID id1 = UUID.fromString("ac696dd8-51f1-4c9b-b7a2-27dff80ad5b7");

        Message messageToBeUpdated = Message
                .builder()
                .flight("AIR-INDIA")
                .messageId(id1)
                .desk("DESK")
                .acknowledge("NO")
                .acknowledgedBy("MRIDUL SEMWAL")
                .aircraftRegistration("UK-07")
                .deskCategory("PILOT")
                .isPublished(0)
                .escalated("NO")
                .priority("HIGH")
                .received("YASH")
                .build();

        Mockito.when(messageService.updateData(messageToBeUpdated, messageToBeUpdated.getMessageId()))
                .thenReturn(updatedMessage);

        String resultJson = objectMapper.writeValueAsString(messageToBeUpdated);

        mockMvc.perform(MockMvcRequestBuilders.put("/accolite/alertmessenger/updateData/ac696dd8-51f1-4c9b-b7a2-27dff80ad5b7")
                .contentType(MediaType.APPLICATION_JSON)
                .content(resultJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.acknowledgedBy")
                        .value(updatedMessage.getAcknowledgedBy()));
    }
    @Test
    public void updateDataTest_ThenThrowException() throws Exception{

        UUID id1 = UUID.fromString("ac696dd8-51f1-4c9b-b7a2-27dff80ad5b7");

        Message messageToBeUpdated = Message
                .builder()
                .flight("AIR-INDIA")
                .messageId(id1)
                .desk("DESK")
                .acknowledge("NO")
                .acknowledgedBy("MRIDUL SEMWAL")
                .aircraftRegistration("UK-07")
                .deskCategory("PILOT")
                .isPublished(0)
                .priority("HIGH")
                .received("YASH")
                .build();

        Mockito.when(messageService.updateData(messageToBeUpdated, messageToBeUpdated.getMessageId()))
                .thenReturn(updatedMessage);

        String resultJson = objectMapper.writeValueAsString(messageToBeUpdated);

        mockMvc.perform(MockMvcRequestBuilders.put("/accolite/alertmessenger/updateData/ac696dd8-51f1-4c9b-b7a2-27dff80ad5b7")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(resultJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void fetchReadForUserTest() throws Exception {
        Mockito.when(messageService.getReadDataForUser())
                .thenReturn(readMessageList);

        mockMvc.perform(MockMvcRequestBuilders.get("/accolite/alertmessenger/fetchreadforuser"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void fetchUnreadForUserTest() throws Exception{
        Mockito.when(messageService.getUnreadDataForUser())
                .thenReturn(unreadMessageList);

        mockMvc.perform(MockMvcRequestBuilders.get("/accolite/alertmessenger/fetchunreadforuser"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void fetchPublishedTest() throws Exception{
        Mockito.when(messageService.getPublishedData())
                .thenReturn(publishedMessageList);

        mockMvc.perform(MockMvcRequestBuilders.get("/accolite/alertmessenger/fetchpublished"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void deleteDataTest() throws Exception{
        UUID id1 = UUID.fromString("ac696dd8-51f1-4c9b-b7a2-27dff80ad5b7");
        Mockito.doNothing().when(messageService).deleteData(id1);

        mockMvc.perform(MockMvcRequestBuilders.delete("/accolite/alertmessenger/deleteData/ac696dd8-51f1-4c9b-b7a2-27dff80ad5b7"))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void acknowledgeDataTest() throws  Exception{
        UUID id1 = UUID.fromString("ac696dd8-51f1-4c9b-b7a2-27dff80ad5b7");

        Message messageToBeAcknowledged = Message
                .builder()
                .flight("AIR-INDIA")
                .messageId(id1)
                .desk("DESK")
                .acknowledge("NO")
                .acknowledgedBy("MRIDUL SEMWAL")
                .aircraftRegistration("UK-07")
                .deskCategory("PILOT")
                .isPublished(0)
                .escalated("NO")
                .priority("HIGH")
                .received("YASH")
                .build();

        Mockito.when(messageService.acknowledgeData(messageToBeAcknowledged, id1))
                .thenReturn(acknowledgedMessage);

        String jsonResult = objectMapper.writeValueAsString(messageToBeAcknowledged);

        mockMvc.perform(MockMvcRequestBuilders.put("/accolite/alertmessenger/acknowledge/ac696dd8-51f1-4c9b-b7a2-27dff80ad5b7")
                .content(jsonResult)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(jsonPath("$.acknowledge")
                        .value(acknowledgedMessage.getAcknowledge()));
    }

    @Test
    public void acknowledgeDataTest_ThenThrowException() throws  Exception{
        UUID id1 = UUID.fromString("ac696dd8-51f1-4c9b-b7a2-27dff80ad5b7");

        Message messageToBeAcknowledged = Message
                .builder()
                .flight("AIR-INDIA")
                .messageId(id1)
                .desk("DESK")
                .acknowledgedBy("MRIDUL SEMWAL")
                .aircraftRegistration("UK-07")
                .deskCategory("PILOT")
                .isPublished(0)
                .received("YASH")
                .build();

        Mockito.when(messageService.acknowledgeData(messageToBeAcknowledged, id1))
                .thenReturn(acknowledgedMessage);

        String jsonResult = objectMapper.writeValueAsString(messageToBeAcknowledged);

        mockMvc.perform(MockMvcRequestBuilders.put("/accolite/alertmessenger/acknowledge/ac696dd8-51f1-4c9b-b7a2-27dff80ad5b7")
                        .content(jsonResult)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void publishDataTest() throws Exception {
        UUID id1 = UUID.fromString("ac696dd8-51f1-4c9b-b7a2-27dff80ad5b7");

        Message messageToBePublished = Message
                .builder()
                .flight("AIR-INDIA")
                .messageId(id1)
                .desk("DESK")
                .acknowledge("NO")
                .acknowledgedBy("MRIDUL SEMWAL")
                .aircraftRegistration("UK-07")
                .deskCategory("PILOT")
                .isPublished(0)
                .escalated("NO")
                .priority("HIGH")
                .received("YASH")
                .build();

        String resultJson = objectMapper.writeValueAsString(messageToBePublished);

        mockMvc.perform(MockMvcRequestBuilders.put("/accolite/alertmessenger/publishing/ac696dd8-51f1-4c9b-b7a2-27dff80ad5b7")
                .content(resultJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isAccepted());
    }

    @Test
    public void whenPublishDataTest_ThenThrowException() throws Exception {
        UUID id1 = UUID.fromString("ac696dd8-51f1-4c9b-b7a2-27dff80ad5b7");

        Message messageToBePublished = Message
                .builder()
                .flight("AIR-INDIA")
                .messageId(id1)
                .desk("DESK")
                .acknowledge("NO")
                .acknowledgedBy("MRIDUL SEMWAL")
                .escalated("NO")
                .priority("HIGH")
                .received("YASH")
                .build();

        String resultJson = objectMapper.writeValueAsString(messageToBePublished);

        mockMvc.perform(MockMvcRequestBuilders.put("/accolite/alertmessenger/publishing/ac696dd8-51f1-4c9b-b7a2-27dff80ad5b7")
                        .content(resultJson)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}