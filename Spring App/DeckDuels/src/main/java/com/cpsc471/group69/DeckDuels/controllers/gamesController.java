package com.cpsc471.group69.DeckDuels.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class gamesController {

    @GetMapping("/games")
    public String games(Model model){
        return "games";
    }
}
