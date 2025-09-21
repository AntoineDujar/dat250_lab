package com.example.demo;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Poll {
    private Integer id;
    @Id
    private String question;
    private Instant publishedAt;
    private Instant validUntil;
    @OneToMany(mappedBy = "poll", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VoteOption> options = new ArrayList<>();
    private String username;
    @ManyToOne(fetch = FetchType.LAZY)
    private User createdBy;

    public Poll() {}

    public Poll (Integer id, String question, List<VoteOption> options, String username) {
        this.question = question;
        this.options = options;
        this.publishedAt = Instant.now();
        this.validUntil = Instant.now();
        this.username = username;
    }

    public Poll(String question, User createdBy) {
        this.question = question;
        this.publishedAt = Instant.now();
        this.validUntil = Instant.now();
        this.createdBy = createdBy;
    }

    public VoteOption addVoteOption(String caption) {
        VoteOption option = new VoteOption(caption, options.size(), this);
        options.add(option);
        return option;
    }


}