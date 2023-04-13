package com.cpsc471.group69.DeckDuels.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class gamesController {

    @GetMapping("/games")
    public String games(Model model, HttpSession session){
        model.addAttribute("username", session.getAttribute("username"));
        return "games";
    }
}
