package com.example.demo;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


import jakarta.validation.constraints.NotNull;

@Data
@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // New auto-incrementing ID field
    private String username;
    private String email;

    @OneToMany
    private List<Poll> createdPolls = new ArrayList<>();
    @OneToMany
    private List<Vote> votedPolls =  new ArrayList<>();

    public User() {}

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public Poll createPoll(String question) {
        Poll poll = new Poll(question, this);
        createdPolls.add(poll);
        return poll;
    }

    public Vote voteFor(VoteOption option) {
        Vote vote = new Vote(option);
        votedPolls.add(vote);
        return vote;
    }

}
