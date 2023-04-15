package com.cpsc471.group69.DeckDuels.game;

// mickey mouse struct
public class PlayerStatInfo {
    public String username;
    // String password;
    public String displayname;
    public int wins;
    public int losses;
    public int mmr;

    public PlayerStatInfo(String user, String dispName, int w, int l, int mmr){
        this.username = user;
        this.displayname = dispName;
        this.wins = w;
        this.losses = l;
        this.mmr = mmr;
    }
}
