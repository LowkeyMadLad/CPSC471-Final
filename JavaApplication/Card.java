

public class Card {
    private long id;
    private String name;
    private int meleeStat;
    private int rangeStat;
    private int shieldStat;

    public Card(long id){
        this.id = id;
        // Call the database to get the rest of the stats.
    }

    public Card(long id, String n, int m, int r, int s){
        this.id = id;
        this.name = n;
        this.meleeStat = m;
        this.rangeStat = r;
        this.shieldStat = s;
    }

    // Only getters for now. Can generate setters with a push of a button if needed
    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getMeleeStat() {
        return this.meleeStat;
    }

    public int getRangeStat() {
        return this.rangeStat;
    }

    public int getShieldStat() {
        return this.shieldStat;
    }
}
