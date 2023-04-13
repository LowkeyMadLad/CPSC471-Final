package com.cpsc471.group69.DeckDuels.sqlFunctions;

import java.sql.SQLException;
import java.util.ArrayList;

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
    public ArrayList<Long> getCards(@PathVariable(value = "username") String username){
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
            return cards;
        } else {
            for(long i : uniques){
                cards.add(i);
            }
        }

        return cards;
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
    public void saveDeck(@RequestBody String[] param){
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
    }
}
