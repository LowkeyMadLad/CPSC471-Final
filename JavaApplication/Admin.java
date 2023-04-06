import java.sql.*;
import java.util.ArrayList;

public class Admin {
    private String username;
    private String password;
    private CardDatabase db;

    public Admin(String username, String password) throws DBConnectException {
        this.username = username;
        this.password = password;
        db = CardDatabase.getDB();
    
    }
        public void createAdminAccount() throws SQLException {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(db.getDBURL(), db.getUSERNAME(), db.getPASSWORD());
            String insertQuery = "INSERT INTO Admin (username, password) VALUES (?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(insertQuery);
            pstmt.setString(1, this.username);
            pstmt.setString(2, this.password);
            int rowCount = pstmt.executeUpdate();
            if (rowCount == 0) {
                throw new SQLException("No rows were changed.");
            }
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    public void banPlayer(Player player) throws SQLException {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(db.getDBURL(), db.getUSERNAME(), db.getPASSWORD());
            conn.setAutoCommit(false);
            String insertQuery = "INSERT INTO Bans (player, admin, datetime) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(insertQuery);
            pstmt.setString(1, player.getUsername());
            pstmt.setString(2, this.username);
            pstmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            int rowCount = pstmt.executeUpdate();
            if (rowCount == 0) {
                throw new SQLException("No rows were changed.");
            }
            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    public void addCard(Card card) throws SQLException {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(db.getDBURL(), db.getUSERNAME(), db.getPASSWORD());
            conn.setAutoCommit(false);
            String insertQuery = "INSERT INTO Card (cardID, name, type, melee, range, guard) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(insertQuery);
            pstmt.setInt(1, card.getId());
            pstmt.setString(2, card.getName());
            pstmt.setBoolean(3, card.getType());
            pstmt.setInt(4, card.getMeleeStat());
            pstmt.setInt(5, card.getRangeStat());
            pstmt.setInt(6, card.getGuardStat());
            int rowCount = pstmt.executeUpdate();
            if (rowCount == 0) {
                throw new SQLException("No rows were changed.");
            }
            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }
}
