package com.example.demo;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;

import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

@Entity
@Data
public class Vote {

    @Id
    private Instant publishedAt;

    @ManyToOne(optional = false)
    private VoteOption voteOption;

    private String username;

    private Integer pollId;

    @ManyToOne(optional = false)
    private VoteOption votesOn;

    public Vote() {}

    public Vote(VoteOption voteOption, String username, Integer pollId) {
        this.publishedAt = Instant.now();
        this.voteOption = voteOption;
        this.username = username;
        this.pollId = pollId;
    }

    public Vote(VoteOption voteOption) {
        this.publishedAt = Instant.now();
        this.voteOption = voteOption;
        this.votesOn = voteOption;
    }
}
