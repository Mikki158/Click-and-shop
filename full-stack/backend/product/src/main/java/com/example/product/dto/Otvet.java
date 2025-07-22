package com.example.product.dto;

import lombok.Data;

@Data
public class Otvet {

    private String text;

    public void setText(String text) {
        this.text = text;
    }
}
