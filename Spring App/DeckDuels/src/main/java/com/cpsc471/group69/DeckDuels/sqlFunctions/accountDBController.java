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
        String jsonStr = null;
        ObjectMapper mapper = new ObjectMapper();
        try{
            jsonStr = mapper.writeValueAsString(un);
        } catch (Exception e){
            e.printStackTrace();
        }
        if (jsonStr == null){
            throw new IllegalArgumentException();
        }
        return jsonStr;
    }

    // player statistics 
    @GetMapping("/account/getmmr/{username}")
    @CrossOrigin(origins = "*")
    public String getMMR(@PathVariable(value = "username") String username){
        int mmr = -1;
        try {
            mmr = db.getPlayerMMR(username);
        } catch (DBConnectException | SQLException e) {
            e.printStackTrace();
        }
        String jsonStr = null;
        ObjectMapper mapper = new ObjectMapper();
        try{
            jsonStr = mapper.writeValueAsString(mmr);
        } catch (Exception e){
            e.printStackTrace();
        }
        if (jsonStr == null){
            throw new IllegalArgumentException();
        }
        return jsonStr;
    }

    @GetMapping("/account/getwins/{username}")
    @CrossOrigin(origins = "*")
    public String getWins(@PathVariable(value = "username") String username){
        int wins = -1;
        try {
            wins = db.getPlayerWins(username);
        } catch (DBConnectException | SQLException e) {
            e.printStackTrace();
        }
        String jsonStr = null;
        ObjectMapper mapper = new ObjectMapper();
        try{
            jsonStr = mapper.writeValueAsString(wins);
        } catch (Exception e){
            e.printStackTrace();
        }
        if (jsonStr == null){
            throw new IllegalArgumentException();
        }
        return jsonStr;
    }

    @GetMapping("/account/getlosses/{username}")
    @CrossOrigin(origins = "*")
    public String getLosses(@PathVariable(value = "username") String username){
        int losses = -1;
        try {
            losses = db.getPlayerLosses(username);
        } catch (DBConnectException | SQLException e) {
            e.printStackTrace();
        }
        String jsonStr = null;
        ObjectMapper mapper = new ObjectMapper();
        try{
            jsonStr = mapper.writeValueAsString(losses);
        } catch (Exception e){
            e.printStackTrace();
        }
        if (jsonStr == null){
            throw new IllegalArgumentException();
        }
        return jsonStr;
    }

    // game history

    @GetMapping("/account/games/{username}")
    @CrossOrigin(origins = "*")
    public String getGameHistory(@PathVariable(value = "username") String username){
        ArrayList<String> history = new ArrayList<String>();

        try {
            history = db.getPlayerGames(username);
        } catch (DBConnectException | SQLException e) {
            e.printStackTrace();
        }

        if(history.isEmpty()){
            // ???
        }

        String jsonStr = null;
        ObjectMapper mapper = new ObjectMapper();
        try{
            jsonStr = mapper.writeValueAsString(history);
        } catch (Exception e){
            e.printStackTrace();
        }
        if (jsonStr == null){
            throw new IllegalArgumentException();
        }
        return jsonStr;
    }

    @GetMapping("/account/gamesbyid/{gameID}")
    @CrossOrigin(origins = "*")
    public String replayGameDetails(@PathVariable(value = "gameID") String uuid){
        String game = "ERROR. GAME NOT FOUND.";
        try {
            game = CardGame.replayGame(game);
        } catch (DBConnectException | SQLException e) {
            e.printStackTrace();
        }

        String jsonStr = null;
        ObjectMapper mapper = new ObjectMapper();
        try{
            jsonStr = mapper.writeValueAsString(game);
        } catch (Exception e){
            e.printStackTrace();
        }
        if (jsonStr == null){
            throw new IllegalArgumentException();
        }
        return jsonStr;
    }
}
