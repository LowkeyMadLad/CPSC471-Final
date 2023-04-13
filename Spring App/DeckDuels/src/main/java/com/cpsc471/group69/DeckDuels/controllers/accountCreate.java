package com.cpsc471.group69.DeckDuels.controllers;

public class accountCreate {
    private String username;
    private String password;
    private String displayName;

    public accountCreate(String username, String password, String displayName) {
        this.username = username;
        this.password = password;
        this.displayName = displayName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDisplayName() {
        return displayName;
    }
}
