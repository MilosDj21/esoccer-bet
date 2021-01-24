package com.example.Bet365Odds.service;

import com.example.Bet365Odds.models.UserModel;
import com.example.Bet365Odds.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserApiService {
    @Autowired
    private UserRepository userRepository;

    public List<UserModel> findAllUsers(){
        return userRepository.findAll();
    }

    public UserModel saveOneUserModel(UserModel user){
        return userRepository.save(user);
    }

    public void deleteOneUser(Integer id){
        userRepository.deleteById(id);
    }
}
