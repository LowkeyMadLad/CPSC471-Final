import java.sql.*;
import java.util.ArrayList;

public class PlayerAccount {
    private static CardDatabase db = null;

    public PlayerAccount() throws DBConnectException {
        db = CardDatabase.getDB();
    }

    public void createPlayer(String username, String password, String displayname) throws DBConnectException, SQLException {
        // Check if the username already exists
        if (checkPlayerExists(username)) {
            throw new IllegalArgumentException("Username already exists.");
        }

        // Insert the new player into the Player table
        String query = "INSERT INTO Player (username, password, wins, losses, mmr, displayname) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = db.getConnection().prepareStatement(query);
        stmt.setString(1, username);
        stmt.setString(2, password);
        stmt.setInt(3, 0);
        stmt.setInt(4, 0);
        stmt.setInt(5, 1000);
        stmt.setString(6, displayname);
        stmt.executeUpdate();
        stmt.close();
    }

    public boolean checkPlayerExists(String username) throws DBConnectException, SQLException {
        String query = "SELECT * FROM Player WHERE username = ?";
        PreparedStatement stmt = db.getConnection().prepareStatement(query);
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();
        boolean exists = rs.next();
        rs.close();
        stmt.close();
        return exists;
    }

    public boolean checkPlayerBanned(String username) throws DBConnectException, SQLException {
        String query = "SELECT * FROM Bans WHERE player = ?";
        PreparedStatement stmt = db.getConnection().prepareStatement(query);
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();
        boolean isBanned = rs.next();
        rs.close();
        stmt.close();
        if (isBanned) {
            throw new BannedPlayerException("You have been banned from the game.");
        }
        return isBanned;
    }

    public void updatePlayerStats(String username, boolean didWin) throws DBConnectException, SQLException {
        // Get the current wins, losses, and MMR for the player
        String query = "SELECT * FROM Player WHERE username = ?";
        PreparedStatement stmt = db.getConnection().prepareStatement(query);
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();
        int wins = 0;
        int losses = 0;
        int mmr = 50;
        if (rs.next()) {
            wins = rs.getInt("wins");
            losses = rs.getInt("losses");
            mmr = rs.getInt("mmr");
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
        stmt = db.getConnection().prepareStatement(query);
        stmt.setInt(1, wins);
        stmt.setInt(2, losses);
        stmt.setInt(3, mmr);
        stmt.setString(4, username);
        stmt.executeUpdate();
        stmt.close();
    }
}
