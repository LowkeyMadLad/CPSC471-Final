package com.cpsc471.group69.DeckDuels.sqlFunctions;

import com.cpsc471.group69.DeckDuels.game.Card;
import com.cpsc471.group69.DeckDuels.game.CardDatabase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
public class gameSelectDBController {

    @GetMapping("/infoget/getdeck/{username}")
    @CrossOrigin(origins = "*")
    public String getDeck(@PathVariable(value = "username") String username){
        ArrayList<Long> cardIDs = null;
        CardDatabase db = CardDatabase.getDB();
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

}
