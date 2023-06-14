package com.accolite.alertMessenger.controller;

import com.accolite.alertMessenger.exception.MessageNotFoundException;
import com.accolite.alertMessenger.model.Message;
import com.accolite.alertMessenger.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/accolite/alertmessenger")
public class MessageController {

    //@Valid will check whether the request body
    //fulfills all the rules given in our model or not

    @Autowired
    private MessageService messageService;

    //Used to save data
    @PostMapping("/saveData")
    public ResponseEntity<Message> saveData(@RequestBody @Valid Message message){
        return new ResponseEntity<Message>(messageService.saveData(message), HttpStatus.OK);
    }

    //Used to fetch all data for admin
    @GetMapping("/fetchData")
    public ResponseEntity<List<Message>> getData(){
            return new ResponseEntity<List<Message>>(messageService.getData(), HttpStatus.OK);
    }

    //Used to get data by id
    @GetMapping("/getbyid/{id}")
    public Message getDataById(@PathVariable("id") UUID id) throws MessageNotFoundException {
        return messageService.getDataById(id);
    }

    //Used to fetch unread data for users
    @GetMapping(value="/fetchunreadforuser")
    public List<Message> getUnreadMessagesForUser(){
        return messageService.getUnreadDataForUser();
    }

    //Used to fetch read data for users
    @GetMapping(value="/fetchreadforuser")
    public List<Message> getReadMessagesForUser(){
        return messageService.getReadDataForUser();
    }

    @GetMapping(value = "/fetchpublished")
    public List<Message> getPublishedMessage(){
        return messageService.getPublishedData();
    }


    //used to delete data
    @DeleteMapping(value="/deleteData/{id}")
    public ResponseEntity<Void> deleteData(@PathVariable("id") UUID id){
        messageService.deleteData(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    //Used to update data
    @PutMapping(value = "/updateData/{id}")
    public ResponseEntity<Message> updateData(@RequestBody @Valid  Message newMessage, @PathVariable("id") UUID id){
        return new ResponseEntity<Message>(messageService.updateData(newMessage, id),HttpStatus.OK);
    }

    //Used to update the data so that it can be shown to the users
    @PutMapping(value="/publishing/{id}")
    public ResponseEntity<Message> publishData(@PathVariable("id") UUID messageId, @RequestBody @Valid Message message) throws MessageNotFoundException {
        return new ResponseEntity<Message>(messageService.publishData(message, messageId),HttpStatus.ACCEPTED);
    }

    @PutMapping(value="/acknowledge/{id}")
    public ResponseEntity<Message> acknowledgeData(@PathVariable("id") UUID messageId, @RequestBody @Valid Message message) throws MessageNotFoundException {
        return new ResponseEntity<Message>(messageService.acknowledgeData(message, messageId),HttpStatus.ACCEPTED);
    }
}
