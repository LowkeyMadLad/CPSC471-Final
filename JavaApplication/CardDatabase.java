
public class CardDatabase {
    private CardDatabase instance;

    private CardDatabase(){
        // Code goes here :D
    }

    // singleton
    public CardDatabase getDB(){
        if (instance == null) {
            instance = new CardDatabase();
        }
        return this.instance;
    }
}
