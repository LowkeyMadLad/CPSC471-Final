package com.cpsc471.group69.DeckDuels.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class seasonController {

    @GetMapping("/season")
    public String season(Model model){
        return "season";
    }
}
