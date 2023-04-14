package com.cpsc471.group69.DeckDuels.game;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;
// import java.time.LocalDate;

public class CardGame {
    // will use java's built in UUID for this
    private static final String id = UUID.randomUUID().toString();

    private boolean gameover;
    private Player winner; 

    private Player player1;
    private Player player2;

    ArrayList<String> moves;
    Timestamp time;

    private static CardDatabase db = null;
    private Scanner reader;

    public CardGame(Player p1, Player p2){
        player1 = p1;
        player2 = p2;
        db = CardDatabase.getDB();
        moves = new ArrayList<String>();
        gameover = false;
        // view
        time = new Timestamp(System.currentTimeMillis());
    }

    public void playGameTerminal() throws DBConnectException, SQLException{
        if(gameover){
            System.out.println("This game has already been played.");
            return;
        }

        time = new Timestamp(System.currentTimeMillis());
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
        System.out.println("HAND: " + p2h.getName());
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
            // player 2 wins
            winner = player2;
            player2.updateStats(true);
            player1.updateStats(false);
        } else{
            // player 1 wins
            winner = player1;
            player1.updateStats(true);
            player2.updateStats(false);
        }
        System.out.println("\nGAME OVER!");
        System.out.println(winner.getDisplayname() + " wins with " + winner.hp + " health remaining!");
        reader.close();

        saveGameToDB();
    }
    private String playerMove(boolean p1){
        if(gameover){
            return "The game is over. No further moves will work.";
        }

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
                System.out.println("THEY WILL ATTACK THIS ROUND.\n");
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
            // case "A" on console version
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
            // case "D" on console version
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

    /**
     * Function to play the game. After every turn, please follow it promptly with verifyTurn().
     * @param p1 Boolean that represents if it is Player 1's turn. Set to false for Player 2.
     * @param attack Boolean to represent the option taken by the player. Set to true if they chose attack,
     * or set to false if they chose defend. 
     * @return Move that will be displayed by the game log. 
     */
    public String playGameTurn(boolean p1, boolean attack){
        if(gameover){
            System.out.println("THE GAME IS ALREADY OVER STOP PLAYING");
            return "The game is over.";
        }
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

        if (attack) {
            // case "A" on terminal version
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
        } else {
            // case "D" on terminal version
            player.defLastTurn = true;
        }
        return readMove(moveseed);
    }

    /**
     * Use after every turn to check if the game is over. If it is,
     * it will save the required information to the database.
     * @return True if that last turn ended the game, false if the game is still in action.
     * @throws DBConnectException
     * @throws SQLException
     */
    public boolean verifyTurn() throws DBConnectException, SQLException{
        if(gameover){
            System.out.println("The game is over!!!");
            return true;
        }
        if (player1.hp <= 0 || player2.hp <= 0) {
            if(player1.hp <= 0){
                // player 2 wins
                winner = player2;
                player2.updateStats(true);
                player1.updateStats(false);
            } else{
                // player 1 wins
                winner = player1;
                player1.updateStats(true);
                player2.updateStats(false);
            }
            saveGameToDB();
            gameover = true;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Ends the game prematurely. 
     * <p>
     * Please DO NOT follow this up with verifyTurn()
     * @param p1 The player quitting. If it is Player 1, set to true; otherwise, if it
     * is Player 2 quitting, set to false.
     * @throws SQLException
     * @throws DBConnectException
     */
    public void quit(boolean p1) throws DBConnectException, SQLException{
        if(p1){
            player1.hp = 0;
        } else {
            player2.hp = 0;
        }
        gameover = true;
        verifyTurn();
    }

    /**
     * For use on game end.
     * @return Player object of the winner of the game.
     */
    public Player getWinner(){
        if(!gameover){
            System.out.println("The game isn't over yet! There still isn't a winner!");
            return null;
        }
        return winner;
    }

    /**
     * Checks if the player defended last turn.
     * @param p1 True if Player 1, false if Player 2.
     * @return True if the player defended on their last turn, false if not. 
     */
    public boolean defendedLastTurn(boolean p1){
        Player player;
        if(p1){
            player = player1;
        } else{
            player = player2;
        }
        return player.defLastTurn;
    }

    private void saveGameToDB() throws DBConnectException, SQLException{
        db.uploadGame(id, time, player1, player2);
        for(String move : moves){
            db.addMove(id, move);
        }
    }

    /**
     * Plays back a previously played game and returns the game log.
     * @param gameID The UUID of the game stored in the database.
     * @return String of the entire game log.
     * @throws DBConnectException
     * @throws SQLException
     */
    public static String replayGame(String gameID) throws DBConnectException, SQLException{
        String game = "";
        ArrayList<String> movelist = db.getGameMoves(gameID);
        for (String move : movelist){
            game += readMove(move) + "\n";
            // System.out.println();
        }
        return game;
    }

    /**
     * SEED: {player}{crit}-{dmg} attack or {player} defend
     * <p>
     * SEED: {1/2}{0/1}{xxx} or {1/2}
     * <p>
     * e.g., player 1 attacks for 105 dmg (no crit) = "10-105"
     * <p>
     * e.g., player 2 defends = "2"
     * @param seed The String seed of the move. 
     * @return String of a move to be represented by the game log.
     * @throws IllegalArgumentException When the seed is invalid.
     */
    private static String readMove(String seed){
        String movestr = "";
        if(seed.charAt(0)!='1' && seed.charAt(0)!='2'){
            throw new IllegalArgumentException("Invalid seed");
        }
        // boolean p1turn = seed.charAt(0) == '1';
        if(seed.length() == 1){
            movestr += "Player " + seed.charAt(0) + " defended.\n";
            return movestr;
        }
        if(seed.charAt(1)!='0' && seed.charAt(1)!='1'){
            throw new IllegalArgumentException("Invalid seed");
        }
        movestr += "Player " + seed.charAt(0) + " attacked.\n";
        boolean crit = seed.charAt(1) == '1';
        if(crit){
            movestr += "CRITICAL HIT!\n";
        }
        String damage = seed.substring(3, seed.length());
        movestr += "Dealt " + damage + " damage.\n";
        return movestr;
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
