package com.cpsc471.group69.DeckDuels.sqlFunctions;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.cpsc471.group69.DeckDuels.game.Card;
import com.cpsc471.group69.DeckDuels.game.CardDatabase;
import com.cpsc471.group69.DeckDuels.game.DBConnectException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class cardDBController {
    CardDatabase db = CardDatabase.getDB();

    @GetMapping("/cards/getallcards/{username}")
    @CrossOrigin(origins = "*")
    public String getCards(@PathVariable(value = "username") String username){
        ArrayList<Long> cards = new ArrayList<Long>();
        ArrayList<Long> uniques = new ArrayList<Long>();

        try {
            // all normal cards
            cards = db.getAllMainCards();
            // all unique cards owned by the user
            uniques = db.getUniqueCards(username);
        } catch (DBConnectException | SQLException e) {
            e.printStackTrace();
        }

        if(uniques.isEmpty()){
            // user owns no unique cards
            String jsonStr = null;
            ObjectMapper mapper = new ObjectMapper();
            try{
                jsonStr = mapper.writeValueAsString(cards);
            } catch (Exception e){
                e.printStackTrace();
            }
            if (jsonStr == null){
                throw new IllegalArgumentException();
            }
            return jsonStr;
            } else {
            for(long i : uniques){
                cards.add(i);
            }
        }

        String jsonStr = null;
        ObjectMapper mapper = new ObjectMapper();
        try{
            jsonStr = mapper.writeValueAsString(cards);
        } catch (Exception e){
            e.printStackTrace();
        }
        if (jsonStr == null){
            throw new IllegalArgumentException();
        }
        return jsonStr;
    }

    @GetMapping("/cards/getcard/{cardID}")
    @CrossOrigin(origins = "*")
    public String getCard(@PathVariable(value = "cardID") String cardID){
        int id = Integer.parseInt(cardID);
        Card card = null;
        try {
            card = new Card(id);
        } catch (DBConnectException | SQLException e) {
            e.printStackTrace();
        }

        String jsonString = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            jsonString = mapper.writeValueAsString(card);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return jsonString;
    }

    @PostMapping("/cards/submitdeck")
    @CrossOrigin(origins = "*")
    public String saveDeck(@RequestBody String[] param){
        try {
            String username = param[0];
            db.clearDeck(username);
            for(int i=1; i <= 6; i++){
                long id = Long.parseLong(param[i]);
                db.addCardToDeck(id, username);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String jsonString = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            jsonString = mapper.writeValueAsString("successful");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

    @GetMapping("/cards/getdeck/{username}")
    @CrossOrigin(origins = "*")
    public String getDeck(@PathVariable(value = "username") String username){
        ArrayList<Long> cardIDs = null;
        String jsonStr = null;
        try{
            cardIDs = db.getDeck(username);
            System.out.println(cardIDs.get(0));
        } catch (Exception e){
            e.printStackTrace();
        }
        if (cardIDs.isEmpty()){
            throw new IllegalArgumentException("cardID's returned null or none");
        }
        ObjectMapper mapper = new ObjectMapper();
        try{
            jsonStr = mapper.writeValueAsString(cardIDs);
        } catch (Exception e){
            e.printStackTrace();
        }
        if (jsonStr == null){
            throw new IllegalArgumentException();
        }
        System.out.println("THIS IS THE JSON STRING: " + jsonStr);
        return jsonStr;
    }
}
