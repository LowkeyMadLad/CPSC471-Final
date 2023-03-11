public class Player {
    private String username;
    private String displayname;
    private Card hand;
    private Card body;
    

    public Player(String username, String displayname, Card hand, Card body) {
        this.username = username;
        this.displayname = displayname;
        this.hand = hand;
        this.body = body;
    }

    public Player() {
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDisplayname() {
        return this.displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public Card getHand() {
        return this.hand;
    }

    public void setHand(Card hand) {
        this.hand = hand;
    }

    public Card getBody() {
        return this.body;
    }

    public void setBody(Card body) {
        this.body = body;
    }

    // This is just a temp meme
    public Card[] getCards(){
        Card[] cards = new Card[2];
        cards[0] = hand;
        cards[1] = body;
        return cards;
    }

    public void updateWL(boolean win){

    }

    public void updateMMR(){

    }

    public void updateSeasonGames(){

    }
}
