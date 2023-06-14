package com.accolite.alertMessenger.repository;

import com.accolite.alertMessenger.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MessageRepo extends JpaRepository<Message, UUID> {

    @Query(value = "select * from message where is_published = 1 and acknowledge = 'NO'", nativeQuery = true)
    public List<Message> getUnreadMessagesForUser();

    @Query(value = "select * from message where is_published = 1 and acknowledge = 'YES'", nativeQuery = true)
    public List<Message> getReadMessagesForUser();

    @Query(value = "select * from message where is_published = 1", nativeQuery = true)
    public List<Message> getPublishedData();

}
