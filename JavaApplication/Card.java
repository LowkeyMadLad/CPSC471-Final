import java.sql.*;

public class Card {
    private long id;
    private String name;
    private boolean type;
    private int meleeStat;
    private int rangeStat;
    private int guardStat;

    private static CardDatabase db = null;

    public Card(long id) throws DBConnectException, SQLException{
        this.id = id;
        db = CardDatabase.getDB();
        int[] stats = db.getCardStats(id);
        if(stats.length > 3) { 
            throw new IllegalArgumentException("Somehow invalid card attributes");
        }
        meleeStat = stats[0];
        rangeStat = stats[1];
        guardStat = stats[2];
        name = db.getCardName(id);
        type = db.getCardType(id);
    }


    public Card(long id, String name, boolean type, int meleeStat, int rangeStat, int guardStat) {
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
    public long getId() {
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
