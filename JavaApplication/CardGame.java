import java.sql.*;
import java.util.ArrayList;

public class CardGame {
    private static int id;

    private Player player1;
    private Player player2;

    private static CardDatabase db = null;

    public CardGame(Player p1, Player p2){
        db = CardDatabase.getDB();
    }

    // Making this static otherwise having the player arguments makes no sense
    public static void updatePlayerStats(Player winner, Player loser){
        
    }

    public void replayGame(long gameID){

    }

    public void readMove(String seed){

    }

    public void saveGameToDB(long gameid, ArrayList<String> moves){

    }

    public Player getPlayer1() {
        return this.player1;
    }

    public Player getPlayer2() {
        return this.player2;
    }

}
