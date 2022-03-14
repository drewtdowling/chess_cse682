package com.cse682.chess_cse682.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCDao {
    private Database db;

    public JDBCDao() {
        this.db = new Database();
    }

    public boolean validate(String username, String password) throws DataAccessException {
        Connection conn = this.db.openConnection();

        try {
            String sql = "SELECT * FROM User WHERE Username = ? and Password = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                this.db.closeConnection(false);
                return true;
            }
//            else {
//                this.db.closeConnection(false);
//                this.register(username, password);
//            }

        }

        catch (SQLException e) {
            e.printStackTrace();
            this.db.closeConnection(false);
            throw new DataAccessException("DAE in validate");
        }

        this.db.closeConnection(false);
        return false;
    }

    public void register(String username, String password) throws DataAccessException {
        Connection conn = this.db.openConnection();

        try {
            String sql = "INSERT INTO User (Username, Password) VALUES (?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, username);
            ps.setString(2, password);

            ps.executeUpdate();

        }
        catch (SQLException e) {
            e.printStackTrace();
            this.db.closeConnection(false);
            throw new DataAccessException("Cnannot create account");
        }

        this.db.closeConnection(true);
    }
}
