package com.accolite.alertMessenger.controller;

import com.accolite.alertMessenger.model.User;
import com.accolite.alertMessenger.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private User user;
    private List<User> userList = new ArrayList<>();

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    BCryptPasswordEncoder bCryptPasswordEncoder =
            new BCryptPasswordEncoder();

    @BeforeEach
    void setup(){
        user = User.builder().userId("mridul")
                .password(bCryptPasswordEncoder.encode("1234"))
                .role("ADMIN")
                .build();

        userList.add(user);

        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void addUserTest() throws Exception{
        User inputUser = User.builder().userId("mridul")
                .password(bCryptPasswordEncoder.encode("1234"))
                .role("ADMIN")
                .build();

        String jsonResult = objectMapper.writeValueAsString(inputUser);
        Mockito.when(userService.addUser(inputUser))
                .thenReturn(user);

        mockMvc.perform(post("/accolite/alertmessenger/addUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonResult))
                .andExpect(status().isOk());
    }

    @Test
    public void getUserTest() throws Exception{

        Mockito.when(userService.getUser())
                .thenReturn(userList);

        mockMvc.perform(get("/accolite/alertmessenger/fetchUser"))
                .andExpect(status().isOk());
    }

    @Test
    public void userLoginTest() throws Exception{
        User inputUser = User.builder().userId("mridul")
                .password(bCryptPasswordEncoder.encode("1234"))
                .role("ADMIN")
                .build();

        String jsonResult = objectMapper.writeValueAsString(inputUser);
        Mockito.when(userService.login(inputUser))
                .thenReturn(user);

        mockMvc.perform(put("/accolite/alertmessenger/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonResult))
                .andExpect(status().isAccepted());
    }

    @Test
    public void userLoginTestForWrongCredentials() throws Exception{
        User inputUser = User.builder().userId("mridul")
                .password(bCryptPasswordEncoder.encode("1234"))
                .role("ADMMIN")
                .build();

        String jsonResult = objectMapper.writeValueAsString(inputUser);
        Mockito.when(userService.login(inputUser))
                .thenReturn(user);

        mockMvc.perform(put("/accolite/alertmessenger/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonResult))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.role")
                        .value(user.getRole()));
    }

    @Test
    public void whenAddingWrongUser_thenThrowException() throws Exception{
        user.setPassword("1234");

        Mockito.when(userService.addUser(user))
                .thenReturn(user);


        mockMvc.perform(post("/accolite/alertmessenger/addUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"userId\":\"\",\n" +
                                "    \"password\":\"1234\",\n" +
                                "    \"role\":\"ADMIN\"\n" +
                                "}"))
                        .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}