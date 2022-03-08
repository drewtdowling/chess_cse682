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
                return true;
            }

        }

        catch (SQLException e) {
            e.printStackTrace();
            this.db.closeConnection(false);
            throw new DataAccessException("DAE in validate");
        }

        return false;
    }
}
