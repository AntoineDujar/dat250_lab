package com.example.demo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class User {
    private String username;
    private String email;

    private List<Poll> createdPolls = new ArrayList<>();
    private List<Vote> votedPolls =  new ArrayList<>();

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

}
