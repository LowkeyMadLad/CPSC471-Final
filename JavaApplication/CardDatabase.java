import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
// import java.time.LocalDate;

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

    public void uploadGame(String id, Date dt, Player player1, Player player2) throws DBConnectException, SQLException{
        initializeConnection();
        String query = "INSERT Game (`gameID`, `datetime`, `player1`, `player2`, `p1body`, `p1hand`, `p2body`, `p2hand`) VALUES (?,?,?,?,?,?,?,?)";
        PreparedStatement myStmt = dbConnect.prepareStatement(query);
        myStmt.setString(1, id);
        myStmt.setDate(2, dt);
        myStmt.setString(3, player1.getUsername());
        myStmt.setString(4, player2.getUsername());
        myStmt.setInt(5, player1.getBody().getId());
        myStmt.setInt(6, player1.getHand().getId());
        myStmt.setInt(7, player2.getBody().getId());
        myStmt.setInt(8, player2.getHand().getId());

        int rowCount = myStmt.executeUpdate();
        if(rowCount == 0){
            throw new SQLException("No rows were changed.");
        }
        
        myStmt.close();
    }

    public void addMove(String gameID, String moveSeed) throws DBConnectException, SQLException{
        initializeConnection();
        String query = "INSERT Move (seed, gameID) VALUES (?, ?)";
        PreparedStatement myStmt = dbConnect.prepareStatement(query);
        // keep in mind seed is put first bc of the table
        // but id is first in the function parameter bc its
        // more visually intuitive
        myStmt.setString(1, moveSeed);
        myStmt.setString(2, gameID);

        int rowCount = myStmt.executeUpdate();
        if(rowCount == 0){
            throw new SQLException("No rows were changed.");
        }
        
        myStmt.close();
    }

    public ArrayList<String> getGameMoves(String gameid) throws DBConnectException, SQLException{
        ArrayList<String> list = new ArrayList<String>();

        initializeConnection();
        String query = "SELECT seed FROM Move WHERE GameID = ?";
        PreparedStatement myStmt = dbConnect.prepareStatement(query);
        myStmt.setString(1, gameid);
        ResultSet results = myStmt.executeQuery();
        
        while(results.next()){
            list.add(results.getString("seed"));
        }

        myStmt.close();
        results.close();
        dbConnect.close();

        return list;
    }

    // for accounts
    public void createPlayer(String username, String password, String displayname) throws DBConnectException, SQLException {
        // Check if the username already exists
        if (checkPlayerExists(username)) {
            throw new IllegalArgumentException("Username already exists.");
        }

        // Insert the new player into the Player table
        String query = "INSERT INTO Player (username, password, wins, losses, mmr, displayname) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = dbConnect.prepareStatement(query);
        stmt.setString(1, username);
        stmt.setString(2, password);
        stmt.setInt(3, 0);
        stmt.setInt(4, 0);
        stmt.setInt(5, 50);
        stmt.setString(6, displayname);

        int rowCount = stmt.executeUpdate();
        if(rowCount == 0){
            throw new SQLException("No rows were changed.");
        }

        stmt.close();
    }

    public boolean checkPlayerExists(String username) throws DBConnectException, SQLException {
        String query = "SELECT * FROM Player WHERE username = ?";
        PreparedStatement stmt = dbConnect.prepareStatement(query);
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();
        boolean exists = rs.next();
        rs.close();
        stmt.close();
        return exists;
    }

    public boolean checkPlayerBanned(String username) throws DBConnectException, SQLException, BannedPlayerException {
        String query = "SELECT * FROM Bans WHERE player = ?";
        PreparedStatement stmt = dbConnect.prepareStatement(query);
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();
        boolean isBanned = rs.next();
        rs.close();
        stmt.close();
        // if (isBanned) {
        //     throw new BannedPlayerException("You have been banned from the game.");
        // }
        return isBanned;
    }

    public void updatePlayerStats(String username, boolean didWin) throws DBConnectException, SQLException {
        // Get the current wins, losses, and MMR for the player
        String query = "SELECT * FROM Player WHERE username = ?";
        PreparedStatement stmt = dbConnect.prepareStatement(query);
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();
        int wins = 0;
        int losses = 0;
        int mmr = 50;
        if (rs.next()) {
            wins = rs.getInt("wins");
            losses = rs.getInt("losses");
            mmr = rs.getInt("mmr");
        } else {
            System.out.println("something broke!!!");
            System.exit(1);
        }
        rs.close();
        stmt.close();

        // Update the wins/losses/MMR
        if (didWin) {
            wins++;
            mmr += 1;
        } else {
            losses++;
            mmr -= 1;
        }
        query = "UPDATE Player SET wins = ?, losses = ?, mmr = ? WHERE username = ?";
        stmt = dbConnect.prepareStatement(query);
        stmt.setInt(1, wins);
        stmt.setInt(2, losses);
        stmt.setInt(3, mmr);
        stmt.setString(4, username);
        int rowCount = stmt.executeUpdate();
        if(rowCount == 0){
            throw new SQLException("No rows were changed.");
        }
        stmt.close();
    }

    public void createAdmin(String username, String password) throws DBConnectException, SQLException {
        // Check if the username already exists
        if (checkPlayerExists(username)) {
            throw new IllegalArgumentException("Username already exists.");
        }

        // Insert the new admin into the Admin table
        String query = "INSERT INTO Admin (username, password) VALUES (?, ?)";
        PreparedStatement stmt = dbConnect.prepareStatement(query);
        stmt.setString(1, username);
        stmt.setString(2, password);
        int rowCount = stmt.executeUpdate();
        if(rowCount == 0){
            throw new SQLException("No rows were changed.");
        }

        stmt.close();
    }

    public void banPlayer(String playerToBan, String admin) throws DBConnectException, SQLException {
        // Check if the player getting banned exists
        if (!checkPlayerExists(playerToBan)) {
            throw new IllegalArgumentException("No such player exists.");
        }

        String query = "INSERT INTO Bans (`player`, `admin`, `datetime`) VALUES (?, ?, ?)";
        PreparedStatement stmt = dbConnect.prepareStatement(query);
        stmt.setString(1, playerToBan);
        stmt.setString(2, admin);
        stmt.setDate(3, Date.valueOf(LocalDate.now()));
        int rowCount = stmt.executeUpdate();
        if(rowCount == 0){
            throw new SQLException("No rows were changed.");
        }

        stmt.close();
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
