package com.example.cardshop.model;

import java.io.Serializable;

public class CardModel implements Serializable {
    public String UID;
    public String name;
    public String price;
    public String description;
    public String image;
    public Integer rating;
    public String game;

    public CardModel(String UID, String name, String price, String description, String image, Integer rating, String game) {
        this.UID = UID;
        this.name = name;
        this.price = price;
        this.description = description;
        this.image = image;
        this.rating = rating;
        this.game = game;
    }
}
