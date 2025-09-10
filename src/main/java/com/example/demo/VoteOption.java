package com.example.demo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class VoteOption {
    private String caption;
    private Integer presentationOrder;
    private List<String> voteUsernames = new ArrayList<>();

    public VoteOption(String caption, Integer presentationOrder) {
        this.caption = caption;
        this.presentationOrder = presentationOrder;
    }
}