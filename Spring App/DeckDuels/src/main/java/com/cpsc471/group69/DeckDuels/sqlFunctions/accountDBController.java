package com.cpsc471.group69.DeckDuels.sqlFunctions;

import java.sql.SQLException;
import java.util.ArrayList;

// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.cpsc471.group69.DeckDuels.game.CardDatabase;
import com.cpsc471.group69.DeckDuels.game.CardGame;
import com.cpsc471.group69.DeckDuels.game.DBConnectException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class accountDBController {
    CardDatabase db = CardDatabase.getDB();

    @GetMapping("/account/getname/{username}")
    @CrossOrigin(origins = "*")
    public String getDisplayName(@PathVariable(value = "username") String username){
        String un = "ERROR 404 - USERNAME NOT FOUND";
        try {
            un = db.getPlayerName(username);
        } catch (DBConnectException | SQLException e) {
            e.printStackTrace();
        }
        return un;
    }

    // player statistics 
    @GetMapping("/account/getmmr/{username}")
    @CrossOrigin(origins = "*")
    public int getMMR(@PathVariable(value = "username") String username){
        int mmr = -1;
        try {
            mmr = db.getPlayerMMR(username);
        } catch (DBConnectException | SQLException e) {
            e.printStackTrace();
        }
        return mmr;
    }

    @GetMapping("/account/getmmr/{username}")
    @CrossOrigin(origins = "*")
    public int getWins(@PathVariable(value = "username") String username){
        int wins = -1;
        try {
            wins = db.getPlayerWins(username);
        } catch (DBConnectException | SQLException e) {
            e.printStackTrace();
        }
        return wins;
    }

    @GetMapping("/account/getmmr/{username}")
    @CrossOrigin(origins = "*")
    public int getLosses(@PathVariable(value = "username") String username){
        int losses = -1;
        try {
            losses = db.getPlayerLosses(username);
        } catch (DBConnectException | SQLException e) {
            e.printStackTrace();
        }
        return losses;
    }

    // game history

    @GetMapping("/account/games/{username}")
    @CrossOrigin(origins = "*")
    public ArrayList<String> getGameHistory(@PathVariable(value = "username") String username){
        ArrayList<String> history = new ArrayList<String>();

        try {
            history = db.getPlayerGames(username);
        } catch (DBConnectException | SQLException e) {
            e.printStackTrace();
        }

        if(history.isEmpty()){
            // ???
        }

        return history;
    }

    @GetMapping("/account/games/{gameID}")
    @CrossOrigin(origins = "*")
    public String replayGameDetails(@PathVariable(value = "gameID") String uuid){
        String game = "ERROR. GAME NOT FOUND.";
        try {
            game = CardGame.replayGame(game);
        } catch (DBConnectException | SQLException e) {
            e.printStackTrace();
        }

        return game;
    }
}
