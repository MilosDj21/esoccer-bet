package com.example.Bet365Odds.controllers;

import com.example.Bet365Odds.models.UserModel;
import com.example.Bet365Odds.service.UserApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class UserController {

    @Autowired
    private UserApiService userApiService;

    @GetMapping("/users")
    public List<UserModel> findAll(){
        return userApiService.findAllUsers();
    }

    @PostMapping("/users")
    public UserModel saveOne(@RequestBody UserModel user){
        return userApiService.saveOneUserModel(user);
    }

    @PutMapping("/users")
    public UserModel updateOne(@RequestBody UserModel user){
        return userApiService.saveOneUserModel(user);
    }

    @DeleteMapping("/users/{id}")
    public void deleteOne(@PathVariable int id){
        userApiService.deleteOneUser(id);
    }

}
