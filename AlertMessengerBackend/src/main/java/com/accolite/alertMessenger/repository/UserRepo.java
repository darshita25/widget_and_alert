package com.accolite.alertMessenger.repository;

import com.accolite.alertMessenger.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    User findByUserId(String userName);
}
