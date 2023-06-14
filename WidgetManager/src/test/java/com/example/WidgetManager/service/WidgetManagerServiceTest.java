package com.example.WidgetManager.service;

import com.example.WidgetManager.exception.NoSuchElementFoundException;
import com.example.WidgetManager.model.WidgetManager;
import com.example.WidgetManager.repository.WidgetManagerRepo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class WidgetManagerServiceTest {

    @Autowired
    private WidgetManagerService widgetManagerService;
    @MockBean
    private WidgetManagerRepo widgetManagerRepo;

    private WidgetManager widgetManager;
    private UUID id;

    @BeforeEach
    void setUp() {

        id = UUID.fromString("f588ef7f-68e1-77d2-a180-541ab458e6b8");
        widgetManager =
                WidgetManager.builder()
                        .widgetId(id)
                        .widgetName("Widget1")
                        .widgetTag("New Widget")
                        .widgetSize("1 x")
                        .widgetStatus("Enabled")
                        .description("Nothing for Now")
                        .build();
        List<WidgetManager> widgetManagerList = new ArrayList<>();
        widgetManagerList.add(widgetManager);
        widgetManagerList.add(widgetManager);


        Mockito.when(widgetManagerRepo.findById(id))
                .thenReturn(Optional.of(widgetManager));

        Mockito.when(widgetManagerRepo.save(widgetManager)).thenReturn(widgetManager);

        Mockito.when(widgetManagerRepo.findAll()).thenReturn(widgetManagerList);

        doNothing().when(widgetManagerRepo).deleteById(id);


    }

    @Test
    @DisplayName("Get Data by valid widget Id")
    public void for_Valid_WidgetId_the_Widget_Data_Should_Return() throws NoSuchElementFoundException {
        UUID id = UUID.fromString("f588ef7f-68e1-77d2-a180-541ab458e6b8");
        WidgetManager foundWidget = widgetManagerService.fetchWidgetById(id);
        assertEquals(id, foundWidget.getWidgetId());
    }

    @Test
    @DisplayName("Save valid widget data")
    public void for_Valid_Widget_Data_Save_Widget() {
        WidgetManager dataSaved = widgetManagerService.saveWidgetData(widgetManager);
        assertEquals(widgetManager, dataSaved);

    }

    @Test
    @DisplayName("Fetch all widget data")
    public void should_Fetch_All_Widget_Data() {
        List<WidgetManager> widgetManagerList = new ArrayList<>();
        widgetManagerList.add(widgetManager);
        widgetManagerList.add(widgetManager);

        List<WidgetManager> fetchedData = widgetManagerService.fetchWidgetData();
        assertEquals(fetchedData, widgetManagerList);
    }

    @Test
    @DisplayName("Should delete one widget by given id")
    public void should_Delete_Widget_By_Given_WidgetID() throws NoSuchElementFoundException {

        widgetManagerService.deleteWidgetById(UUID.fromString("f588ef7f-68e1-77d2-a180-541ab458e6b8"));
        verify(widgetManagerRepo, times(1)).deleteById(UUID.fromString("f588ef7f-68e1-77d2-a180-541ab458e6b8"));


    }

    @Test
    @DisplayName("Update Widget Tag when update by id is called")
    public void givenWidgetObject_updateWidget_thenReturnUpdatedWidget() throws NoSuchElementFoundException {

        widgetManager.setWidgetTag("Updated Widget Tag");
        widgetManager.setWidgetName("Updated Widget Name");

        WidgetManager updatedData = widgetManagerService.updateWidget(id, widgetManager);
        assertEquals(updatedData.getWidgetName(), "Updated Widget Name");
        assertEquals(updatedData.getWidgetTag(), "Updated Widget Tag");

    }

    @Test
    @DisplayName("when find widget bi id called throws exception")
    public void givenWidgetId_findWidgetById_throwException() {

        assertThrows(NoSuchElementFoundException.class, () -> {
            widgetManagerService.fetchWidgetById(UUID.fromString("f588e88f-68e1-77d2-a180-541ab458e6b8"));
        });


    }

    @Test
    @DisplayName("for invalid widget id updateWidgetById should throw exception")
    public void givenInvalidWidgetId_updateById_throwException() {

        assertThrows(NoSuchElementFoundException.class, () -> {
            widgetManagerService.updateWidget(UUID.fromString("f599e88f-68e1-77d2-a180-541ab458e6b8"), widgetManager);
        });

        verify(widgetManagerRepo, never()).save(any(WidgetManager.class));
    }

    @Test
    @DisplayName("given Invalid Widget Id should throw exception on DeleteById")
    public void givenInvalidWidgetId_DeleteByID_throwException() {
        assertThrows(NoSuchElementFoundException.class, () -> {
            widgetManagerService.deleteWidgetById(UUID.fromString("f599e88f-68e1-77d2-a180-541ab458e6b8"));
        });
        verify(widgetManagerRepo, never()).deleteById(any(UUID.class));
    }
}