package com.cpsc471.group69.DeckDuels.controllers;

import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.cpsc471.group69.DeckDuels.game.CardDatabase;
import com.cpsc471.group69.DeckDuels.game.DBConnectException;
import com.cpsc471.group69.DeckDuels.game.SeasonPeak;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class seasonController {
    CardDatabase db = CardDatabase.getDB();

    @GetMapping("/season/getallseasonpeaks/{username}")
    public ArrayList<String> getAllSeasonPeaks(@PathVariable(value = "username") String username){
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
        return list;
    }

}
