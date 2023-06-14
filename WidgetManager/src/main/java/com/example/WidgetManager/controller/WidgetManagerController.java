package com.example.WidgetManager.controller;

import com.example.WidgetManager.exception.NoSuchElementFoundException;
import com.example.WidgetManager.model.WidgetManager;
import com.example.WidgetManager.service.WidgetManagerService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/accolite/WidgetManager")
public class WidgetManagerController {
    @Autowired
    private WidgetManagerService widgetManagerService;

    private final Logger LOGGER=LoggerFactory.getLogger(WidgetManagerController.class);
    @PostMapping("/saveWidget")
    public WidgetManager saveWidgetData(@RequestBody @Valid WidgetManager widgetManager) {
        LOGGER.info("Inside saveWidgetData of WidgetManageController");
        return widgetManagerService.saveWidgetData(widgetManager);
    }

    @GetMapping("/widgets")
    public List<WidgetManager> fetchWidgetData() {
        LOGGER.info("Inside fetchWidgetData of WidgetManageController");
        return widgetManagerService.fetchWidgetData();
    }

    @GetMapping("/widget/{id}")
    public WidgetManager fetchWidgetById(@PathVariable("id") UUID id) throws NoSuchElementFoundException {
        LOGGER.info("Inside fetchWidgetById of WidgetManageController");
        return widgetManagerService.fetchWidgetById(id);
    }

    @DeleteMapping("/widget/{id}")
    public void deleteWidgetById(@PathVariable("id") UUID id) throws NoSuchElementFoundException {
        LOGGER.info("Inside fetchWidgetById of WidgetManageController");
        widgetManagerService.deleteWidgetById(id);
    }

    @PutMapping("/widget/{id}")
    public WidgetManager updateWidget(@PathVariable("id") @Valid UUID id, @RequestBody
    WidgetManager widgetManager) throws NoSuchElementFoundException {
        LOGGER.info("Inside fetchWidgetById of WidgetManageController");
        return widgetManagerService.updateWidget(id, widgetManager);
    }


}
