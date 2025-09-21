package com.example.demo;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class VoteOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String caption;

    private Integer presentationOrder;

    @ElementCollection
    private List<String> voteUsernames = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private Poll poll;

    public VoteOption() {}

    public VoteOption(String caption, Integer presentationOrder) {
        this.caption = caption;
        this.presentationOrder = presentationOrder;
    }

    public VoteOption(String caption, Integer presentationOrder, Poll poll) {
        this.caption = caption;
        this.presentationOrder = presentationOrder;
        this.poll = poll;
    }
}
