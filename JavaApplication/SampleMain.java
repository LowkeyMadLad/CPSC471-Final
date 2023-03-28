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
        game.playGame();

        // CardGame.replayGame("da97cc56-1eed-4db3-916f-fc1acc30392f");
    }
}
