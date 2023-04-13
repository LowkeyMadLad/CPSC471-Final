package com.cpsc471.group69.DeckDuels.controllers;

public class accountInfo {
    private String username;
    private String password;

    public accountInfo(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
