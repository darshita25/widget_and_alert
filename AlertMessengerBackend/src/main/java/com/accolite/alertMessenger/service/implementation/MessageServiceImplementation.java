package com.accolite.alertMessenger.service.implementation;

import com.accolite.alertMessenger.exception.MessageNotFoundException;
import com.accolite.alertMessenger.model.Message;
import com.accolite.alertMessenger.model.User;
import com.accolite.alertMessenger.repository.MessageRepo;
import com.accolite.alertMessenger.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MessageServiceImplementation implements MessageService {

    @Autowired
    private MessageRepo messageRepo;

    @Override
    public Message saveData(Message message) {
        return messageRepo.save(message);
    }

    @Override
    public List<Message> getData() {
        return messageRepo.findAll();
    }

    @Override
    public void deleteData(UUID id) {
        messageRepo.deleteById(id);
    }

    @Override
    public Message updateData(Message newMessage, UUID id) {
        return messageRepo.findById(id)
                .map(message ->{
                      message.setAircraftRegistration(newMessage.getAircraftRegistration());
                      message.setFlight(newMessage.getFlight());
                      message.setDesk(newMessage.getDesk());
                      message.setDeskCategory(newMessage.getDeskCategory());
                      message.setEscalated(newMessage.getEscalated());
                      message.setAcknowledge(newMessage.getAcknowledge());
                      message.setAcknowledgedBy(newMessage.getAcknowledgedBy());
                      message.setReceived(newMessage.getReceived());
                      message.setPriority(newMessage.getPriority());
                    return messageRepo.save(message);
                })
                .orElseGet(()->{
                    return messageRepo.save(newMessage);
                });
    }

    @Override
    public Message publishData(Message message, UUID messageId) throws MessageNotFoundException {
        if(messageRepo.findById(messageId).isPresent()){
            Message messageToBeUpdated = messageRepo.findById(messageId).get();
            messageToBeUpdated.setIsPublished(1);
            return messageRepo.save(messageToBeUpdated);
        }
        else throw new MessageNotFoundException("Message cannot be published as no message was found with the id : "
                + messageId + ". Please provide a valid messageId");

    }

    @Override
    public Message getDataById(UUID messageId) throws MessageNotFoundException {

        if(messageRepo.findById(messageId).isPresent()){
           return messageRepo.findById(messageId).get();
        }
        else throw new MessageNotFoundException("No message was found with the id : "
                + messageId + ". Please give a valid messageId");
    }

    @Override
    public Message acknowledgeData(Message message, UUID messageId) throws MessageNotFoundException {

        if(messageRepo.findById(messageId).isPresent()){
            Message messageToBeUpdated = messageRepo.findById(messageId).get();
            messageToBeUpdated.setAcknowledge("YES");
            return messageRepo.save(messageToBeUpdated);
        }
        else throw new MessageNotFoundException("Message cannot be acknowledged as no message was found with the id : "
                + messageId + ". Please give a valid messageId");
    }

    @Override
    public List<Message> getUnreadDataForUser() {
        return messageRepo.getUnreadMessagesForUser();
    }

    @Override
    public List<Message> getReadDataForUser() {
        return messageRepo.getReadMessagesForUser();
    }

    @Override
    public List<Message> getPublishedData() {
        return messageRepo.getPublishedData();
    }


}
