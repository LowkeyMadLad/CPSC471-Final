package com.cpsc471.group69.DeckDuels.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class homeController {

    @GetMapping("/home")
    public String home(Model model){

        return "index";
    }

}
