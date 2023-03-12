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

    public int[] getCardStats(long id) throws DBConnectException, SQLException{
        initializeConnection();
        String query = "SELECT melee, range, guard FROM Card WHERE cardID = ?";
        PreparedStatement myStmt = dbConnect.prepareStatement(query);
        myStmt.setLong(1, id);
        ResultSet results = myStmt.executeQuery();

        int[] stats = new int[3];     
        stats[0] = results.getInt("melee");
        stats[1] = results.getInt("range");
        stats[2] = results.getInt("guard");

        myStmt.close();
        results.close();
        dbConnect.close();

        return stats;
    }

    public String[] getCardNameType(long id) throws DBConnectException, SQLException{
        initializeConnection();
        String query = "SELECT name, type FROM Card WHERE cardID = ?";
        PreparedStatement myStmt = dbConnect.prepareStatement(query);
        myStmt.setLong(1, id);
        ResultSet results = myStmt.executeQuery();

        String[] strings = new String[2];     
        strings[0] = results.getString("name");
        strings[1] = results.getString("type");

        myStmt.close();
        results.close();
        dbConnect.close();

        return strings;
    }

    public String getPlayerName(String username) throws DBConnectException, SQLException{
        initializeConnection();
        String query = "SELECT displayname FROM Player WHERE username = ?";
        PreparedStatement myStmt = dbConnect.prepareStatement(query);
        myStmt.setString(1, username);
        ResultSet results = myStmt.executeQuery();

        String displayname = results.getString("displayname");

        myStmt.close();
        results.close();
        dbConnect.close();

        return displayname;
    }

    /**
     * Initializes connection to the database
     * @throws DBConnectException
     */
    public void initializeConnection() throws DBConnectException{
        try {
            dbConnect = DriverManager.getConnection(DBURL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBConnectException("Failed to connect to the Database. Check DBURL, USERNAME, PASSWORD.");
        }

        if (dbConnect == null) {
            throw new DBConnectException("Failed to connect to the Database. Check DBURL, USERNAME, PASSWORD.");
        }
    }
}
