package com.cpsc471.group69.DeckDuels.game;

// mickey mouse struct
public class AccountInfo {
    String username;
    // String password;
    String displayname;
    int wins;
    int losses;
    int mmr;

    public AccountInfo(String user, String dispName, int w, int l, int mmr){
        this.username = user;
        this.displayname = dispName;
        this.wins = w;
        this.losses = l;
        this.mmr = mmr;
    }
}
