package com.cpsc471.group69.DeckDuels.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class cardsController {

    @GetMapping("/cards")
    public String cards(Model model){
        return "cards";
    }
}
