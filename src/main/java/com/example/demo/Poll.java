package com.example.demo;

import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class Poll {
    private Integer id;
    private String question;
    private Instant publishedAt;
    private Instant validUntil;
    private List<VoteOption> options;
    private String username;

    public Poll (Integer id, String question, List<VoteOption> options, String username) {
        this.id = id;
        this.question = question;
        this.options = options;
        this.publishedAt = Instant.now();
        this.validUntil = Instant.now();
        this.username = username;
    }

}