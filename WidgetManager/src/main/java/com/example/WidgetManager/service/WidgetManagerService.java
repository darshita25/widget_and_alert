package com.example.WidgetManager.service;

import com.example.WidgetManager.exception.NoSuchElementFoundException;
import com.example.WidgetManager.model.WidgetManager;

import java.util.List;
import java.util.UUID;

public interface WidgetManagerService {
    WidgetManager saveWidgetData(WidgetManager widgetManager);

    List<WidgetManager> fetchWidgetData();

    WidgetManager fetchWidgetById(UUID id) throws NoSuchElementFoundException;

    void deleteWidgetById(UUID id) throws NoSuchElementFoundException;

    WidgetManager updateWidget(UUID id, WidgetManager widgetManager) throws NoSuchElementFoundException;

}
