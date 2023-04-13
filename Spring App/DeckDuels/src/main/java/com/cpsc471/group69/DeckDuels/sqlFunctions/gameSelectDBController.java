package com.cpsc471.group69.DeckDuels.sqlFunctions;

import com.cpsc471.group69.DeckDuels.game.Card;
import com.cpsc471.group69.DeckDuels.game.CardDatabase;
import com.cpsc471.group69.DeckDuels.game.CardGame;
import com.cpsc471.group69.DeckDuels.game.DBConnectException;
import com.cpsc471.group69.DeckDuels.game.Player;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@RestController
public class gameSelectDBController {
    CardDatabase db = CardDatabase.getDB();

    CardGame game = null;

    @GetMapping("/infoget/getdeck/{username}")
    @CrossOrigin(origins = "*")
    public String getDeck(@PathVariable(value = "username") String username){
        ArrayList<Long> cardIDs = null;
        String jsonStr = null;
        try{
            cardIDs = db.getDeck(username);
        } catch (Exception e){
            e.printStackTrace();
        }
        if (cardIDs.isEmpty()){
            throw new IllegalArgumentException("cardID's returned null or none");
        }
        List<Card> cardArr = new ArrayList<Card>();
        for (int i = 0; i < cardIDs.size(); i++){
            try {
                Card temp = new Card(cardIDs.get(i).intValue());
                cardArr.add(temp);
            } catch (Exception e){
                e.printStackTrace();
            }

        }
        ObjectMapper mapper = new ObjectMapper();
        try{
            jsonStr = mapper.writeValueAsString(cardArr);
        } catch (Exception e){
            e.printStackTrace();
        }
        if (jsonStr == null){
            throw new IllegalArgumentException();
        }
        return jsonStr;
    }

    // start game
    @PostMapping("/game/start")
    @CrossOrigin(origins = "*")
    public String startGame(@RequestBody String[] cards){
        // bot random cards
        int randomhand = 100 + getRandomNumber(1, 8);
        int randombody = getRandomNumber(1, 8);
        try {
            String user = cards[0];
            String body = cards[1];
            String hand = cards[2];
            Player p1 = new Player(user, new Card(Integer.parseInt(hand)), new Card(Integer.parseInt(body)));
            Player p2 = new Player("BOT", new Card(randomhand), new Card(randombody));
            game = new CardGame(p1, p2);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        int[] botcards = new int[2];
        botcards[0] = randombody;
        botcards[1] = randomhand;
        String jsonString = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            jsonString = mapper.writeValueAsString(botcards);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

    @GetMapping("/game/{turn}")
    @CrossOrigin(origins = "*")
    public String playTurn(@PathVariable(value = "turn") String[] turnStr){
        // hi ryan
        // the sent in parameter should be two booleans
        // index 0 represents IF IT IS PLAYER 1'S TURN
        // [false = Player2], and [true = Player1]
        // index 1 represents IF IT IS AN ATTACK
        // [false = DEFEND], and [true = ATTACK]
        // :thumbs_up:
        boolean p1turn = Boolean.parseBoolean(turnStr[0]);
        boolean attack = Boolean.parseBoolean(turnStr[1]);

        String move = game.playGameTurn(p1turn, attack);
        String jsonString = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            jsonString = mapper.writeValueAsString(move);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return jsonString;
    }

    /**
     * USE AFTER EVERY TURN but not after a quit
     * @return true if that last turn ended the game, false if its still going
     */
    @GetMapping("/game/verify")
    @CrossOrigin(origins = "*")
    public String verifyTurn(){
        boolean gameover = false;
        try {
            gameover = game.verifyTurn();
        } catch (DBConnectException | SQLException e) {
            e.printStackTrace();
        }

        String jsonString = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            jsonString = mapper.writeValueAsString(gameover);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return jsonString;
    }

    @GetMapping("game/playerhp/{p1}")
    @CrossOrigin(origins = "*")
    public String getPlayerHP(@PathVariable(value = "{p1}") String p1){
        // p1 should be a boolean
        // true if player 1
        // false if it is the bot's hp you want
        double hp = 300.0;
        if(Boolean.parseBoolean(p1)){
            hp = game.getPlayer1().hp;
        } else{
            hp = game.getPlayer2().hp;
        }

        String jsonString = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            jsonString = mapper.writeValueAsString(hp);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return jsonString;
    }

    @GetMapping("game/end")
    @CrossOrigin(origins = "*")
    public String getEndMessage(){
        Player winner = game.getWinner();
        String msg = winner.getDisplayname() + " wins with " + winner.hp + " health remaining!";

        String jsonString = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            jsonString = mapper.writeValueAsString(msg);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return jsonString;
    }

    @GetMapping("game/turncheck/{p1turn}")
    @CrossOrigin(origins = "*")
    public String defendedLastTurn(@PathVariable(value = "p1turn") String p1){
        // p1turn should be a boolean
        boolean defLT = game.defendedLastTurn(Boolean.parseBoolean(p1));

        String jsonString = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            jsonString = mapper.writeValueAsString(defLT);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return jsonString;
    }

    // for random int between range
    private int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

}

