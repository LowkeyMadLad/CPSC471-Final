import java.sql.*;

public class Card {
    private long id;
    private String name;
    private String type;
    private int meleeStat;
    private int rangeStat;
    private int guardStat;

    private static CardDatabase db = null;

    public Card(long id) throws DBConnectException, SQLException{
        this.id = id;
        db = CardDatabase.getDB();
        int[] stats = db.getCardStats(id);
        String[] nametype = db.getCardNameType(id);
        if(stats.length > 3 || nametype.length > 2) { 
            throw new IllegalArgumentException("Somehow invalid card attributes");
        }
        meleeStat = stats[0];
        rangeStat = stats[1];
        guardStat = stats[2];
        name = nametype[0];
        type = nametype[1];
    }


    public Card(long id, String name, String type, int meleeStat, int rangeStat, int guardStat) {
        this.id = id;
        this.name = name;
        this.type = type;
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

    public String getType() {
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
