package com.example.apidichvuguimailgiavang.model;

import lombok.Data;

import java.util.LinkedHashMap;

@Data
public class Gold {
    private String metal;
    private String currency;
    private double price;
    private double ch;
    private double chp;

    public Gold toGold(LinkedHashMap<String, Object> data){
        this.metal = (String) data.get("metal");
        this.currency = (String) data.get("currency");
        this.price = (double) data.get("price");
        this.ch = (double) data.get("ch");
        this.chp = (double) data.get("chp");
        return this;
    }
}
