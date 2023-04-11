import java.sql.*;

// Player objects exist only during a game. 
public class Player {
    private String username;

    // game attributes
    private String displayname;
    private Card hand;
    private Card body;
    public double hp;
    public boolean defLastTurn;
    
    private static CardDatabase db = null;

    public Player(String username, Card hand, Card body) throws DBConnectException, SQLException{
        db = CardDatabase.getDB();
        this.username = username;
        this.displayname = db.getPlayerName(username);
        this.hand = hand;
        this.body = body;
        // base hp for a game will be 300 for now
        this.hp = 300.0;
        this.defLastTurn = false;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDisplayname() {
        return this.displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public Card getHand() {
        return this.hand;
    }

    public void setHand(Card hand) {
        this.hand = hand;
    }

    public Card getBody() {
        return this.body;
    }

    public void setBody(Card body) {
        this.body = body;
    }

    // This is just a temp meme
    public Card[] getCards(){
        Card[] cards = new Card[2];
        cards[0] = hand;
        cards[1] = body;
        return cards;
    }

    public void updateStats(boolean win) throws DBConnectException, SQLException{
        // SKIP IF BOT ACCOUNT
        if(username.equals("BOT")) return;

        db.updatePlayerStats(username, win);
    }

    // both of the below functions are now done by updatePlayerStats()

    // public void updateMMR(int points){
    //     // db update mmr
    // }

    // private void updateSeasonStats(){
    //     // db update season games
    // }
}
