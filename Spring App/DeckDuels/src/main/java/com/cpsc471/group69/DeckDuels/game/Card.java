package com.cpsc471.group69.DeckDuels.game;

import java.sql.*;

public class Card {
    private int id;
    private String name;
    private boolean type;
    // F=body, T=hand ^^^
    private int meleeStat;
    private int rangeStat;
    private int guardStat;

    private static CardDatabase db = null;

    public Card(int id) throws DBConnectException, SQLException{
        this.id = id;
        db = CardDatabase.getDB();
        int[] stats = db.getCardStats(id);
        if(stats.length > 3) { 
            throw new IllegalArgumentException("Somehow invalid card attributes");
        }
        meleeStat = stats[0];
        rangeStat = stats[1];
        guardStat = stats[2];
        if(meleeStat + rangeStat + guardStat != 100){
            throw new IllegalArgumentException("Invalid card stats -- do not equal 100");
        }
        name = db.getCardName(id);
        type = db.getCardType(id);
    }


    public Card(int id, String name, boolean type, int meleeStat, int rangeStat, int guardStat) {
        this.id = id;
        this.name = name;
        this.type = type;
        if(meleeStat + rangeStat + guardStat != 100){
            throw new IllegalArgumentException("Invalid card stats -- do not equal 100");
        }
        this.meleeStat = meleeStat;
        this.rangeStat = rangeStat;
        this.guardStat = guardStat;
    }


    // Only getters for now. Can generate setters with a push of a button if needed
    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public boolean getType() {
        return this.type;
    }

    public int getMeleeStat() {
        return this.meleeStat;
    }

    public int getRangeStat() {
        return this.rangeStat;
    }

    public int getGuardStat() {
        return this.guardStat;
    }
}
