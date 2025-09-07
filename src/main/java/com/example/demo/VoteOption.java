package com.example.demo;

import lombok.Data;

@Data
public class VoteOption {
    private String caption;
    private Integer presentationOrder;

    public VoteOption(String caption, Integer presentationOrder) {
        this.caption = caption;
        this.presentationOrder = presentationOrder;
    }
}