package com.example.Bet365Odds.service;

import com.example.Bet365Odds.encryption.PassEncryption;
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
        String hashedPass = PassEncryption.encrypt(user.getPassword());
        user.setPassword(hashedPass);
        return userRepository.save(user);
    }

    public void deleteOneUser(Integer id){
        userRepository.deleteById(id);
    }

    public UserModel login(UserModel user){
        String hashedPass = PassEncryption.encrypt(user.getPassword());
        List<UserModel> users = findAllUsers();
        for(UserModel m: users){
            if(user.getUsername().equals(m.getUsername()) && hashedPass.equals(m.getPassword())){
                if(m.getLoggedIn() == 1){
                    return null;
                }else if(m.getLoggedIn() == 0){
                    m.setLoggedIn(1);
                    userRepository.save(m);
                    return m;
                }
            }
        }
        return null;
    }

    public UserModel logout(UserModel user){
        List<UserModel> users = findAllUsers();
        for(UserModel m: users){
            if(user.getId().equals(m.getId())){
                m.setLoggedIn(0);
                userRepository.save(m);
                return m;
            }
        }
        return null;
    }


}
