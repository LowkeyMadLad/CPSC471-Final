package com.cpsc471.group69.DeckDuels.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class gameRunController {

    @GetMapping("/gameRun")
    @CrossOrigin(origins = "*")
    public String gameRun(Model model){
        model.addAttribute("username", "dannyp");
        model.addAttribute("cardhandID", "103");
        model.addAttribute("cardbodyID", "3");
        return "gameRun";
    }
}
