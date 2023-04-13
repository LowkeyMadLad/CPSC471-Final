package com.cpsc471.group69.DeckDuels.controllers;

import com.cpsc471.group69.DeckDuels.game.CardDatabase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class accountController {

    @GetMapping("/account")
    public String account(Model model, HttpSession session){
        model.addAttribute("username", session.getAttribute("username"));
        return "account";
    }

    @PostMapping("/accountlogin")
    public String accountSignIn(@RequestBody accountInfo account, HttpServletRequest request){
        CardDatabase db = CardDatabase.getDB();
        boolean exists = false;
        try {
            exists = db.loginPlayer(account.getUsername(), account.getPassword());
        } catch (Exception e){
            e.printStackTrace();
        }
        if (exists == true){
            request.getSession().setAttribute("username", account.getUsername());
            return "loggedIn";
        } else {
            return "account";
        }

    }

    @PostMapping("/createaccount")
    public String accountCreation(@RequestBody accountCreate account, HttpServletRequest request){
        CardDatabase db = CardDatabase.getDB();
        try {
            db.createPlayer(account.getUsername(), account.getPassword(), account.getDisplayName());
        } catch (Exception e){

        }
        request.getSession().setAttribute("username", account.getUsername());
        return "loggedIn";
    }

}
