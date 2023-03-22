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
                double p_atkmelee = player.getHand().getMeleeStat();
                double p_atkrange = player.getHand().getRangeStat();
                double p_atkguard = player.getHand().getRangeStat();
                double e_defmelee = enemy.getBody().getMeleeStat();
                double e_defrange = enemy.getBody().getRangeStat();
                double e_defguard = enemy.getBody().getGuardStat();
                double damage = 0;
                // (MOVE - [100 - STRONG]) + (MOVE - WEAK)
                // = (2*MOVE + STRONG - WEAK - 100)
                // ASSERT NOT NEGATIVE
                double meleedmg = Math.max(0, 2*p_atkmelee + e_defguard - e_defrange - 100);
                double rangedmg = Math.max(0, 2*p_atkrange + e_defmelee - e_defguard - 100);
                double guarddmg = Math.max(0, 2*p_atkguard + e_defrange - e_defmelee - 100);
                damage = meleedmg + rangedmg + guarddmg;

                // crit chance
                double critRNG = Math.random();
                // defending gives you a 50% crit chance next turn
                // otherwise default crit chance is 15%
                if((player.defLastTurn && critRNG <= 0.5) || critRNG <= 0.15){
                    damage *= 2;
                }
                // defending makes you take half damage
                if(enemy.defLastTurn){
                    damage /= 2;
                }
                // deal damage and reset defense status
                enemy.hp -= damage;
                player.defLastTurn = false;
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
