package com.example.Bet365Odds.models;

import com.sun.istack.NotNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class UserModel {
    @Id
    @GeneratedValue
    private Integer id;
    @NotNull
    @Column(unique = true)
    private String username;
    @NotNull
    private String password;
    @NotNull
    private Integer loggedIn;
    @NotNull
    private String subscribedUntil;

    public UserModel() {
    }

    public UserModel(Integer id, @NotNull String username, @NotNull String password, @NotNull Integer loggedIn, @NotNull String subscribedUntil) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.loggedIn = loggedIn;
        this.subscribedUntil = subscribedUntil;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(Integer loggedIn) {
        this.loggedIn = loggedIn;
    }

    public String getSubscribedUntil() {
        return subscribedUntil;
    }

    public void setSubscribedUntil(String subscribedUntil) {
        this.subscribedUntil = subscribedUntil;
    }
}
