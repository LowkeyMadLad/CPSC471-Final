package com.cpsc471.group69.DeckDuels.controllers;

public class gameInfoStart {
    private String username;
    private String cardhandID;
    private String cardbodyID;

    public gameInfoStart(String username, String cardhandID, String cardbodyID) {
        this.username = username;
        this.cardhandID = cardhandID;
        this.cardbodyID = cardbodyID;
    }

    public String getUsername() {
        return username;
    }

    public String getCardhandID() {
        return cardhandID;
    }

    public String getCardbodyID() {
        return cardbodyID;
    }
}
