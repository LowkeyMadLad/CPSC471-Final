package com.cpsc471.group69.DeckDuels.sqlFunctions;

import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.cpsc471.group69.DeckDuels.game.CardDatabase;
import com.cpsc471.group69.DeckDuels.game.DBConnectException;

@RestController
public class cardDBController {

    @GetMapping("/cards/getallcards/{username}")
    public ArrayList<Long> getCards(@PathVariable(value = "username") String username){
        CardDatabase db = CardDatabase.getDB();

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
}
