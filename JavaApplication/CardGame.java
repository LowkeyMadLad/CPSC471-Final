import java.sql.*;

public class CardGame {
    private Player player1;
    private Player player2;

    private static CardDatabase db = null;

    public CardGame(Player p1, Player p2){
        db = CardDatabase.getDB();
    }

    // Making this static otherwise having the player arguments makes no sense
    public static void updatePlayerStats(Player winner, Player loser){
        
    }

    public String runGame(){

        return "hi";
    }

    public void readGame(String seed){

    }

    public void saveGameToDB(){

    }

    public Player getPlayer1() {
        return this.player1;
    }

    public Player getPlayer2() {
        return this.player2;
    }

}
