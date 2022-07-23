package com.example.cardshop.model;

import java.io.Serializable;

public class CardModel implements Serializable {
    private String UID;
    private String name;
    private String price;
    private String description;
    private String image;
    private Double rating;
    private String game;

    public CardModel(String UID, String name, String price, String description, String image, Double rating, String game) {
        this.UID = UID;
        this.name = name;
        this.price = price;
        this.description = description;
        this.image = image;
        this.rating = rating;
        this.game = game;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }
}
