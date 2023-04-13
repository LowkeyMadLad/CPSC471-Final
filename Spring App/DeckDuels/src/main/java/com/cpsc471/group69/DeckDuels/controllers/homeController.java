package com.cpsc471.group69.DeckDuels.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller // The controllers are for webpages. Rest controllers are for API calls
public class homeController {

    @GetMapping("/home") // Get mapping is a GET command
    public String home(Model model, HttpSession session){ // This is for getting the webpage
        model.addAttribute("username", session.getAttribute("username"));
        return "index"; // This return is the page name without the .html in templates
    }

}
