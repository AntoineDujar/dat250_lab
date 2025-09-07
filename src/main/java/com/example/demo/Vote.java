package com.example.demo;

import lombok.Data;

import java.time.Instant;

@Data
public class Vote {
    private Instant publishedAt;
    private VoteOption voteOption;
    private String username;
    private Integer pollId;

    public Vote (VoteOption voteOption, String username, Integer pollId) {
        this.publishedAt = Instant.now();
        this.voteOption = voteOption;
        this.username = username;
        this.pollId = pollId;
    }
}