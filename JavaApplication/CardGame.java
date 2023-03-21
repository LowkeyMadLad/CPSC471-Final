import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class CardGame {
    private static int id;

    private Player player1;
    private Player player2;

    ArrayList<String> moves;

    private static CardDatabase db = null;
    

    public CardGame(Player p1, Player p2){
        db = CardDatabase.getDB();
        moves = new ArrayList<String>();
    }

    public void playGame(){
        boolean p1turn = true;

        // SEED: player# + attack/defend + damage dealt
        // {1/2}{a/d}{xxx}
        String moveseed;
        
        while(player1.hp > 0 || player2.hp > 0){
            moveseed = "";
            
            playerMove(p1turn);

            moves.add(moveseed);
            p1turn = !p1turn;
        }
    }
    private String playerMove(boolean p1){
        Player player;
        Player enemy;
        String moveseed;
        if(p1){
            player = player1;
            enemy = player2;
            moveseed = "1";
        } else{
            player = player2;
            enemy = player1;
            moveseed = "2";
        }

        String choice = "";
        Scanner reader = new Scanner(System.in);  
        while(!choice.equals("A") || !choice.equals("D")){
            // players can only defend once in a row
            if(player.defLastTurn){
                System.out.println(player.getDisplayname() + " DEFENDED LAST ROUND.");
                System.out.println("THEY WILL ATTACK THIS ROUND.");
                choice = "A";
                player.defLastTurn = false;
                break;
            }
            System.out.println(player.getDisplayname() + " ENTER:");
            System.out.println("ATTACK (A) OR DEFEND (D): ");
            choice = reader.nextLine().toUpperCase(); 
            reader.close();
        }
        switch (choice) {
            case "A":
                // damage is just W - L i think
                if(enemy.defLastTurn){
                    // half the damage?
                }
                break;
            case "D":

                player.defLastTurn = true;
                break;
            default:
                System.out.println("how tf did we get here");
                System.exit(1);
                break;
        }
        return moveseed;
    }
    private void saveGameToDB(){

    }

    // Making this static otherwise having the player arguments makes no sense
    public static void updatePlayerStats(Player winner, Player loser){
        
    }

    public static void replayGame(long gameID){

    }
    private void readMove(String seed){

    }

    public Player getPlayer1() {
        return this.player1;
    }

    public Player getPlayer2() {
        return this.player2;
    }

}
