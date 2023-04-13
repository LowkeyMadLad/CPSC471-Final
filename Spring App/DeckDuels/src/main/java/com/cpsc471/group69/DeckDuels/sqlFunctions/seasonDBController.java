package com.cpsc471.group69.DeckDuels.sqlFunctions;

import com.cpsc471.group69.DeckDuels.game.CardDatabase;
import com.cpsc471.group69.DeckDuels.game.DBConnectException;
import com.cpsc471.group69.DeckDuels.game.SeasonPeak;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.ArrayList;

@RestController
public class seasonDBController {

    CardDatabase db = CardDatabase.getDB();

    @GetMapping("/season/getallseasonpeaks/{username}")
    @CrossOrigin(origins = "*")
    public String getAllSeasonPeaks(@PathVariable(value = "username") String username){
        ArrayList<String> list = new ArrayList<String>();
        ArrayList<SeasonPeak> seasonList = new ArrayList<SeasonPeak>();

        try {
            seasonList = db.getSeasonPeaks(username);
        } catch (DBConnectException | SQLException e) {
            e.printStackTrace();
        }

        if(seasonList.isEmpty()){
            // something to display nothing?
        }

        String jsonStr;
        ObjectMapper mapper = new ObjectMapper();
        for(SeasonPeak sp : seasonList){
            try {
                jsonStr = mapper.writeValueAsString(sp);
                list.add(jsonStr);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        // returns array list of json strings
        jsonStr = null;
        mapper = new ObjectMapper();
        try{
            jsonStr = mapper.writeValueAsString(list);
        } catch (Exception e){
            e.printStackTrace();
        }
        if (jsonStr == null){
            throw new IllegalArgumentException();
        }
        return jsonStr;
    }

}
