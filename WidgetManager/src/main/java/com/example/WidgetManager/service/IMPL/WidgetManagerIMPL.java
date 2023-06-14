package com.example.WidgetManager.service.IMPL;

import com.example.WidgetManager.exception.NoSuchElementFoundException;
import com.example.WidgetManager.model.WidgetManager;
import com.example.WidgetManager.repository.WidgetManagerRepo;
import com.example.WidgetManager.service.WidgetManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;


@Service
public class WidgetManagerIMPL implements WidgetManagerService {
    @Autowired
    private WidgetManagerRepo widgetManagerRepo;

    public WidgetManagerIMPL(WidgetManagerRepo widgetManagerRepo){
        this.widgetManagerRepo=widgetManagerRepo;
    }

    @Override
    public WidgetManager saveWidgetData(WidgetManager widgetManager) {

        return widgetManagerRepo.save(widgetManager);
    }

    @Override
    public List<WidgetManager> fetchWidgetData() {
      return  widgetManagerRepo.findAll();
    }

    @Override
    public WidgetManager fetchWidgetById(UUID id) throws NoSuchElementFoundException {
       WidgetManager data = widgetManagerRepo.findById(id).orElse(null);
       if(data!=null){
        return data;
       }else {
           throw new NoSuchElementFoundException("Widget with specified id "+ id +" not found");
       }
    }

    @Override
    public void deleteWidgetById(UUID id) throws NoSuchElementFoundException {
        WidgetManager data = widgetManagerRepo.findById(id).orElse(null);
        if(data!=null){
            widgetManagerRepo.deleteById(id);
        }else {
            throw new NoSuchElementFoundException("Widget with specified id "+ id +" not found");
        }

    }

    @Override
    public WidgetManager updateWidget(UUID id, WidgetManager widgetManager) throws NoSuchElementFoundException {
        WidgetManager wData= widgetManagerRepo.findById(id).orElse(null);

        if(wData!=null) {
            if (Objects.nonNull(widgetManager.getWidgetName()) && !"".equals(widgetManager.getWidgetName())) {
                wData.setWidgetName(widgetManager.getWidgetName());
            }
            if (Objects.nonNull(widgetManager.getWidgetTag()) && !"".equals(widgetManager.getWidgetTag())) {
                wData.setWidgetTag(widgetManager.getWidgetTag());
            }
            if (Objects.nonNull(widgetManager.getWidgetStatus()) && !"".equals(widgetManager.getWidgetStatus())) {
                wData.setWidgetStatus(widgetManager.getWidgetStatus());
            }
            if (Objects.nonNull(widgetManager.getWidgetSize()) && !"".equals(widgetManager.getWidgetSize())) {
                wData.setWidgetSize(widgetManager.getWidgetSize());
            }
            if (Objects.nonNull(widgetManager.getDescription()) && !"".equals(widgetManager.getDescription())) {
                wData.setDescription(widgetManager.getDescription());
            }
            return widgetManagerRepo.save(wData);
        }
        else {
            throw new NoSuchElementFoundException("Widget with specified id "+ id +" not found");
        }
    }


}
