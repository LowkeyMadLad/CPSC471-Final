import java.sql.*;
// import java.util.ArrayList;

public class CardDatabase {
    private static CardDatabase instance = null;

    // database stuff
    private final String DBURL = "jdbc:mysql://localhost/CARD_DATABASE";
    private final String USERNAME = "student";
    private final String PASSWORD = "ensf";
    private Connection dbConnect;

    private CardDatabase(){
        // Code goes here :D
    }

    // singleton
    public static CardDatabase getDB(){
        if (instance == null) {
            instance = new CardDatabase();
        }
        return instance;
    }

    public int[] getCardStats(int id) throws DBConnectException, SQLException{
        initializeConnection();
        String query = "SELECT `melee`, `range`, `guard` FROM Card WHERE cardID = ?;";
        PreparedStatement myStmt = dbConnect.prepareStatement(query);
        myStmt.setLong(1, id);
        ResultSet results = myStmt.executeQuery();

        int[] stats = new int[3];    
        if(results.next()){
            // System.out.println("testing if this runs");
            stats[0] = results.getInt("melee");
            stats[1] = results.getInt("range");
            stats[2] = results.getInt("guard");
        } else{
            System.out.println("something broke!!!");
            System.exit(1);
        }

        myStmt.close();
        results.close();
        dbConnect.close();

        return stats;
    }

    public String getCardName(long id) throws DBConnectException, SQLException{
        initializeConnection();
        String query = "SELECT `name` FROM Card WHERE cardID = ?;";
        PreparedStatement myStmt = dbConnect.prepareStatement(query);
        myStmt.setLong(1, id);
        ResultSet results = myStmt.executeQuery();
   
        String result = "impossible value to have";
        if(results.next()){
            result = results.getString("name");
        } else {
            System.out.println("something broke!!!");
            System.exit(1);
        }
        

        myStmt.close();
        results.close();
        dbConnect.close();

        return result;
    }

    // false (0) = body card, true (1) = hand card
    public boolean getCardType(long id) throws DBConnectException, SQLException{
        initializeConnection();
        String query = "SELECT `type` FROM Card WHERE cardID = ?;";
        PreparedStatement myStmt = dbConnect.prepareStatement(query);
        myStmt.setLong(1, id);
        ResultSet results = myStmt.executeQuery();
   
        boolean result = false; // shouldnt ever not get set by next line
        if(results.next()){
            result = results.getBoolean("type");
        } else {
            System.out.println("something broke!!!");
            System.exit(1);
        }

        myStmt.close();
        results.close();
        dbConnect.close();

        return result;
    }

    public String getPlayerName(String username) throws DBConnectException, SQLException{
        initializeConnection();
        String query = "SELECT `displayname` FROM Player WHERE `username` = ?;";
        PreparedStatement myStmt = dbConnect.prepareStatement(query);
        myStmt.setString(1, username);
        ResultSet results = myStmt.executeQuery();

        String displayname = "you cant get this";
        if(results.next()){
            displayname = results.getString("displayname");
        } else {
            System.out.println("something broke!!!");
            System.exit(1);
        }

        myStmt.close();
        results.close();
        dbConnect.close();

        return displayname;
    }

    /**
     * Initializes connection to the database
     * @throws DBConnectException
     */
    private void initializeConnection() throws DBConnectException{
        try {
            dbConnect = DriverManager.getConnection(DBURL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            String errmsg = e.getMessage();
            throw new DBConnectException("Failed to connect to the Database. Check DBURL, USERNAME, PASSWORD.\n" + errmsg);
        }

        if (dbConnect == null) {
            throw new DBConnectException("Failed to connect to the Database. Check DBURL, USERNAME, PASSWORD.");
        }
    }
}
