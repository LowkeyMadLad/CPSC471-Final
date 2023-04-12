
package com.cpsc471.group69.DeckDuels.game;
import java.sql.SQLException;

public class SampleMain {
    public static void main(String[] args) throws DBConnectException, SQLException {
        System.out.println("WELCOME TO OUR CARD GAME - TERMINAL EDITION");
        System.out.println("-------------------------------------------");

        // hard coded cards
        Card p1B = new Card(1);
        Card p1H = new Card(103);
        Card p2B = new Card(5);
        Card p2H = new Card(105);

        // hard coded players
        Player p1 = new Player("bob", p1H, p1B);
        Player p2 = new Player("dannyp", p2H, p2B);

        // how the card picking should work:
        // every player has a deck (table in database) which contains 3 body cards
        // and 3 hand cards. p1 and p2 are chosen by rng i guess lol. anyway, the
        // players are presented with their decks and p1 chooses their hand card. 
        // then p2 chooses both their hand and body card, then p1 chooses their
        // body card. players can see eachothers picks but not decks.

        CardGame game = new CardGame(p1, p2);

        // game.playGameTerminal();
        // CardGame.replayGame("da97cc56-1eed-4db3-916f-fc1acc30392f");


        // FAKE FRONT-END:
        // ------------------------
        // you will need to make the buttons unpressable when its not your turn.
        // assuming you can do that, i will just put it as a while loop for now.
        boolean p1turn = true;
        // vvv UNCOMMENT TO TEST RAGE QUIT vvv
        // int turn = 1;
        // ^^^ UNCOMMENT TO TEST RAGE QUIT ^^^
        while(true){
            // button is pressed by player
            // im just gonna randomize them both for now (0 = atk, 1 = def)
            int randomChoice = (int)Math.round(Math.random());
            // YOU WILL HAVE TO MAKE IT SO A PLAYER CANNOT DEFEND TWICE IN A ROW
            // YOU CAN DO THAT VERY EASILY BY UTILIZING THE GIVEN FUNCTION
            if(game.defendedLastTurn(p1turn)){
                // block the defense button from being pressed
                // or give a warning or something.
                // for this sample, i will just set their move to attack.
                System.out.println("def last turn, doing attack");
                randomChoice = 0;
            }
            String move;
            // if they press the attack button
            if(randomChoice == 0){
                // returns the move that will display in the log
                move = game.playGameTurn(p1turn, true);
                // display move in the log
                System.out.println(move);
            }
            // if they press the defend button
            else{
                // returns the move that will display in the log
                move = game.playGameTurn(p1turn, false);
                System.out.println(move);
            }
            // VERIFY THE MOVE!!!
            boolean gameEnd = game.verifyTurn();
            if(gameEnd){
                // display in the game log that the game is over
                System.out.println("\nGAME OVER!");
                Player winner = game.getWinner();
                System.out.println(winner.getDisplayname() + " wins with " + winner.hp + " health remaining!");
                // i can break out of this treacherous hell of a while loop
                // (end the game)
                break;
            }

            // vvv UNCOMMENT TO TEST RAGE QUIT vvv
            // if(turn == 4){
            //     // you can make a player quit with the following function
            //     game.quit(p1turn);
            //     // do NOT verifyTurn() afterwards.
            //     // now display in game log
            //     System.out.println("\nThe match was forfeited.");
            //     Player winner = game.getWinner();
            //     System.out.println(winner.getDisplayname() + " wins with " + winner.hp + " health remaining.");
            //     // end the game
            //     break;
            // }
            // turn++;
            // ^^^ UNCOMMENT TO TEST RAGE QUIT ^^^

            // cycling the turns. you wont need to do this, instead you will
            // call the needed functions on button press.
            p1turn = !p1turn;
        }

    }

}
