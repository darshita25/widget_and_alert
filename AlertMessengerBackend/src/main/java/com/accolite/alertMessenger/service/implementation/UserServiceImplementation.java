package com.accolite.alertMessenger.service.implementation;

import com.accolite.alertMessenger.model.User;
import com.accolite.alertMessenger.repository.UserRepo;
import com.accolite.alertMessenger.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.sql.SQLOutput;
import java.util.List;

@Service
public class UserServiceImplementation implements UserService {

    BCryptPasswordEncoder bCryptPasswordEncoder =
            new BCryptPasswordEncoder();
    @Autowired
    private UserRepo userRepo;


    @Override
    public User addUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return  userRepo.save(user);
    }
    @Override
    public User login(User user) throws Exception {
       User data = userRepo.findByUserId(user.getUserId());
       if(data!=null){
           if(bCryptPasswordEncoder.matches(user.getPassword(),data.getPassword())){
               data.setUserId(null);
               data.setPassword(null);
               return data;
           }else{
               System.out.println("Wrong Password");
                 throw new Exception("Wrong Password");
           }
       }
       else{
           System.out.println("null");
           throw new Exception("Usr not Found");
       }
    }
    @Override
    public List<User> getUser() {
        return userRepo.findAll();
    }


}
