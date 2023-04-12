package com.cpsc471.group69.DeckDuels.game;

// import java.sql.*;

// class to store information. does not store who it pertains to, as it does not need to.
// solely for display.
public class SeasonPeak {
    // Timestamp startDate;
    int season;
    int games;
    int mmr;

    public SeasonPeak(int season, int games, int mmr){
        this.season = season;
        this.games = games;
        this.mmr = mmr;
    }
}
