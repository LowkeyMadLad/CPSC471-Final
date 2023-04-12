package com.cpsc471.group69.DeckDuels.game;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class gameController {
    private CardGame game;
    private Card p1h;
    private Card p1b;
    private Card p2h;
    private Card p2b;
    private Player p1;
    private Player p2;

    @GetMapping("/")
    public List<String> test(){
        return List.of("test", "test2", "test3");
    }

    @PostMapping("/start-game")
    @CrossOrigin(origins = "*")
    public List<String> gameStart(@RequestBody postStartGame playerItems){
        // The goal is to have the following be in the list
        // Item 1 is player 1 username followed by hand card then body card. Same for player 2.
        System.out.println(playerItems.getUsernamep1());
        System.out.println(playerItems.getUsernamep2());
        try{
            this.p1h = new Card(Integer.parseInt(playerItems.getP1h()));
            this.p1b = new Card(Integer.parseInt(playerItems.getP1b()));
            this.p2h = new Card(Integer.parseInt(playerItems.getP2h()));
            this.p2b = new Card(Integer.parseInt(playerItems.getP2b()));
            this.p1 = new Player(playerItems.getUsernamep1(), p1h, p1b);
            this.p2 = new Player(playerItems.getUsernamep2(), p2h, p2b);
            this.game = new CardGame(p1, p2);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return List.of(p1.getDisplayname(), p2.getDisplayname(), "Success");
    }

    @PostMapping("/play-turn")
    public List<String> playTurn(@RequestBody List<String> playerActions){

        return List.of("beep", "boop");
    }

}
