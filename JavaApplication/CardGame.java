import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;
import java.time.LocalDate;

public class CardGame {
    // will use java's built in UUID for this
    private static final String id = UUID.randomUUID().toString();;

    private Player player1;
    private Player player2;

    ArrayList<String> moves;
    Date time;

    private static CardDatabase db = null;
    private Scanner reader;

    public CardGame(Player p1, Player p2){
        player1 = p1;
        player2 = p2;
        db = CardDatabase.getDB();
        moves = new ArrayList<String>();
    }

    public void playGame() throws DBConnectException, SQLException{
        Date.valueOf(LocalDate.now());
        Card p1b = player1.getBody();
        Card p1h = player1.getHand();
        Card p2b = player2.getBody();
        Card p2h = player2.getHand();
        System.out.println("PLAYER 1: " + player1.getDisplayname());
        System.out.println("BODY: " + p1b.getName());
        System.out.println("MELEE: "+p1b.getMeleeStat()+"   RANGE: "+p1b.getRangeStat()+"   GUARD: "+p1b.getGuardStat());
        System.out.println("HAND: " + p1h.getName());
        System.out.println("MELEE: "+p1h.getMeleeStat()+"   RANGE: "+p1h.getRangeStat()+"   GUARD: "+p1h.getGuardStat());
        System.out.println();
        System.out.println("PLAYER 2: " + player2.getDisplayname());
        System.out.println("BODY: " + p2b.getName());
        System.out.println("MELEE: "+p2b.getMeleeStat()+"   RANGE: "+p2b.getRangeStat()+"   GUARD: "+p2b.getGuardStat());
        System.out.println("HAND: " + p1h.getName());
        System.out.println("MELEE: "+p2h.getMeleeStat()+"   RANGE: "+p2h.getRangeStat()+"   GUARD: "+p2h.getGuardStat());
        System.out.println();

        boolean p1turn = true;

        // SEED: player# + attack/defend + damage dealt
        // {1/2}{a/d}{xxx}
        String moveseed;
        
        while(player1.hp > 0 && player2.hp > 0){
            moveseed = playerMove(p1turn);
            readMove(moveseed);
            moves.add(moveseed);
            p1turn = !p1turn;
        }

        Player winner;
        if(player1.hp <= 0){
            winner = player2;
        } else{
            winner = player1;
        }
        System.out.println("\n GAME OVER!");
        System.out.println(winner.getDisplayname() + " wins with " + winner.hp + " health remaining!");
        reader.close();

        saveGameToDB();
    }
    private String playerMove(boolean p1){
        Player player;
        Player enemy;
        // SEED: {player}{crit}-{dmg} attack or {player} defend
        // SEED: {1/2}{0/1}{xxx} or {1/2}
        // e.g., player 1 attacks for 105 dmg (no crit) = "10-105"
        // e.g., player 2 defends = "2"
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
        System.out.println();
        String choice = "";
        reader = new Scanner(System.in);  
        while(!choice.equals("A") && !choice.equals("D")){
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
        }
        // reader.close(); // error-ridden rat
        switch (choice) {
            case "A":
                double p_atkmelee = player.getHand().getMeleeStat();
                double p_atkrange = player.getHand().getRangeStat();
                double p_atkguard = player.getHand().getGuardStat();
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
                    moveseed += "1";
                } else {
                    moveseed += "0";
                }
                // defending makes you take half damage
                if(enemy.defLastTurn){
                    damage /= 2;
                }
                // deal damage and reset defense status
                damage *= randomDouble(0.9, 1.1);
                enemy.hp -= damage;
                moveseed += "-" + String.valueOf(damage);
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
    private void saveGameToDB() throws DBConnectException, SQLException{
        db.uploadGame(id, time, player1, player2);
        for(String move : moves){
            db.addMove(id, move);
        }
    }

    public static void replayGame(String gameID) throws DBConnectException, SQLException{
        ArrayList<String> movelist = db.getGameMoves(gameID);
        for (String move : movelist){
            readMove(move);
            System.out.println();
        }
    }
    private static void readMove(String seed){
        // SEED: {player}{crit}-{dmg} attack or {player} defend
        // SEED: {1/2}{0/1}{xxx} or {1/2}
        // e.g., player 1 attacks for 105 dmg (no crit) = "10-105"
        // e.g., player 2 defends = "2"
        if(seed.charAt(0)!='1' && seed.charAt(0)!='2'){
            throw new IllegalArgumentException("Invalid seed");
        }
        // boolean p1turn = seed.charAt(0) == '1';
        if(seed.length() == 1){
            System.out.println("Player " + seed.charAt(0) + " defended.");
            return;
        }
        if(seed.charAt(1)!='0' && seed.charAt(1)!='1'){
            throw new IllegalArgumentException("Invalid seed");
        }
        System.out.println("Player " + seed.charAt(0) + " attacked.");
        boolean crit = seed.charAt(1) == '1';
        if(crit){
            System.out.println("CRITICAL HIT!");
        }
        String damage = seed.substring(3, seed.length());
        System.out.println("Dealt " + damage + " damage.");
    }

    public Player getPlayer1() {
        return this.player1;
    }

    public Player getPlayer2() {
        return this.player2;
    }


    private double randomDouble(double min, double max) {
        return (Math.random() * (max - min)) + min;
    }
}
