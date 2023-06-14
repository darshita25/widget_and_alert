package com.example.WidgetManager.controller;

import com.example.WidgetManager.exception.NoSuchElementFoundException;
import com.example.WidgetManager.model.WidgetManager;
import com.example.WidgetManager.service.WidgetManagerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WidgetManagerController.class)
class WidgetManagerControllerTest {

     @Autowired
     private MockMvc mockMvc;

    @MockBean
    private WidgetManagerService widgetManagerService;

    @Autowired
    private WebApplicationContext context;
    private WidgetManager widgetManager;
    UUID id;

    @BeforeEach
    void setUp() {

        id = UUID.fromString("2f771de7-c2b8-4c35-86bc-63d973633974");

        widgetManager =
                WidgetManager.builder()
                        .widgetId(id)
                        .widgetName("Widget1")
                        .widgetTag("New Widget")
                        .widgetSize("1 x")
                        .widgetStatus("Enabled")
                        .description("Nothing for Now")
                        .build();

        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }


    @Test
    void saveWidgetData() throws Exception {


        WidgetManager newWidgetData =
                WidgetManager.builder()
                        .widgetName("Widget1")
                        .widgetTag("New Widget")
                        .widgetSize("1 x")
                        .widgetStatus("Enabled")
                        .description("Nothing for Now")
                        .build();

        Mockito.when(widgetManagerService.saveWidgetData(newWidgetData))
                .thenReturn(widgetManager);


        mockMvc.perform(MockMvcRequestBuilders.post("/accolite/WidgetManager/saveWidget")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\"widgetName\": \"Widget1\",\n" +
                        "\"widgetTag\": \"New Widget\",\n" +
                        "\"widgetStatus\": \"Enabled\",\n" +
                        " \"widgetSize\": \"1 x\",\n" +
                        "\"description\": \"Nothing for Nowt\"\n" +
                        "    }"))
                .andExpect(
                        status().isOk());



    }

    @Test
    void saveWidgetData_thenReturn400() throws Exception {

        mockMvc.perform(post("/accolite/WidgetManager/saveWidget")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("  {\n" +
                                "\"widgetTag\": \"New Widget\",\n" +
                                "\"widgetStatus\": \"Enabled\",\n" +
                                " \"widgetSize\": \"1 x\",\n" +
                                "\"description\": \"Nothing for Nowt\"\n" +
                                "    }"))
                .andExpect(
                        status().isBadRequest());



    }
    @Test
    void fetchWidgetData() throws Exception {
        List<WidgetManager> fetchDataList = new ArrayList<>();
        fetchDataList.add(widgetManager);
        fetchDataList.add(widgetManager);

        Mockito.when(widgetManagerService.fetchWidgetData())
                .thenReturn(fetchDataList);

        mockMvc.perform(get("/accolite/WidgetManager/widgets"))
                .andExpect(status().isOk());

    }


    @Test
    void fetchWidgetById() throws Exception {

        Mockito.when(widgetManagerService.fetchWidgetById(id))
                .thenReturn(widgetManager);

        mockMvc.perform(get("/accolite/WidgetManager/widget/2f771de7-c2b8-4c35-86bc-63d973633974")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.widgetName")
                        .value(widgetManager.getWidgetName()));

    }

    @Test
    void fetchWidgetById_thenReturn500() throws Exception {

        Mockito.when(widgetManagerService.fetchWidgetById(id))
                .thenThrow(NoSuchElementFoundException.class);
        mockMvc.perform(get("/accolite/WidgetManager/widget/2f771de7-c2b8-4c35-86bc-63d973633974")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());

    }

    @Test
    void deleteWidgetById() throws Exception {
        Mockito.doNothing().when(widgetManagerService).deleteWidgetById(id);
        mockMvc.perform(delete("/accolite/WidgetManager/widget/2f771de7-c2b8-4c35-86bc-63d973633974")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status()
                        .isOk());

    }

    @Test
    void updateWidget() throws Exception {
        WidgetManager newWidgetData =
                WidgetManager.builder()
                        .widgetName("Widget1")
                        .widgetTag("New Widget")
                        .widgetSize("1 x")
                        .widgetStatus("Enabled")
                        .description("Nothing for Now")
                        .build();

        Mockito.when(widgetManagerService.updateWidget(id,newWidgetData)).thenReturn(widgetManager);

        mockMvc.perform(put("/accolite/WidgetManager/widget/2f771de7-c2b8-4c35-86bc-63d973633974")
                .contentType(MediaType.APPLICATION_JSON)
                        .content("  {\n" +
                                "\"widgetName\": \"Widget1\",\n" +
                                "\"widgetTag\": \"New Widget\",\n" +
                                "\"widgetStatus\": \"Enabled\",\n" +
                                " \"widgetSize\": \"1 x\",\n" +
                                "\"description\": \"Nothing for Nowt\"\n" +
                                "}"))
                .andExpect(status().isOk());
    }

    @Test
    void updateWidget_thenReturn500() throws Exception {
        WidgetManager newWidgetData =
                WidgetManager.builder()
                        .widgetName("Widget1")
                        .widgetTag("New Widget")
                        .widgetSize("1 x")
                        .widgetStatus("Enabled")
                        .description("Nothing for Now")
                        .build();

        Mockito.when(widgetManagerService.updateWidget(id,newWidgetData)).thenThrow(NoSuchElementFoundException.class);

        mockMvc.perform(put("/accolite/WidgetManager/widget/2f771de7-c2b8-4c35-86bc-63d973633974")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "\"widgetName\": \"Widget1\",\n" +
                                "\"widgetTag\": \"New Widget\",\n" +
                                "\"widgetStatus\": \"Enabled\",\n" +
                                " \"widgetSize\": \"1 x\",\n" +
                                "\"description\": \"Nothing for Now\"\n" +
                                "}"))
                .andExpect(status().isInternalServerError());
    }


}