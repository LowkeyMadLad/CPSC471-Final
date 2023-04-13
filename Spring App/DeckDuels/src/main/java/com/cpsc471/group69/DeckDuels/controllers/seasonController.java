package com.cpsc471.group69.DeckDuels.controllers;

import java.sql.SQLException;
import java.util.ArrayList;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.cpsc471.group69.DeckDuels.game.CardDatabase;
import com.cpsc471.group69.DeckDuels.game.DBConnectException;
import com.cpsc471.group69.DeckDuels.game.SeasonPeak;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class seasonController {

    @GetMapping("/season")
    public String season(Model model, HttpSession session){
        model.addAttribute("username", session.getAttribute("username"));
        return "season";
    }
}
