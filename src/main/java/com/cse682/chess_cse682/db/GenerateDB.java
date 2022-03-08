package com.cse682.chess_cse682.db;

import javafx.scene.chart.PieChart;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class GenerateDB {
    private Database db;

    public GenerateDB() {
        this.db = new Database();
    }

    public void generateUserTable() throws DataAccessException {
        Connection conn = db.openConnection();

        try (Statement sm = conn.createStatement()) {
            String sqlSM = "CREATE TABLE IF NOT EXISTS User " +
                    "(" +
                    "UID INTEGER," +
                    "Username TEXT NOT NULL UNIQUE," +
                    "Password TEXT," +
                    "PRIMARY KEY(UID)" +
                    ")";

            sm.executeUpdate(sqlSM);
            db.closeConnection(true);
        }
        catch (DataAccessException e) {
            db.closeConnection(false);
            throw e;
        }
        catch (SQLException e) {
            db.closeConnection(false);
            throw new DataAccessException("SQL Error Encountered while creating User Table");
        }
    }

    public void clearUserTable() throws DataAccessException {
        Connection conn = db.openConnection();

        try (Statement sm = conn.createStatement()) {
            String sqlSM = "DELETE FROM User";

            sm.executeUpdate(sqlSM);
            db.closeConnection(true);
        }
        catch (DataAccessException e) {
            db.closeConnection(false);
            throw e;
        }
        catch (SQLException e) {
            db.closeConnection(false);
            throw new DataAccessException("SQL Error Encountered while clearing User Table");
        }
    }

    public void generateStatsTable() throws DataAccessException {
        Connection conn = db.openConnection();

        try (Statement sm = conn.createStatement()) {
            String sqlSM = "CREATE TABLE IF NOT EXISTS Stats " +
                    "(" +
                    "SID INTEGER," +
                    "Username TEXT NOT NULL UNIQUE," +
                    "Wins INTEGER NOT NULL," +
                    "Draws INTEGER NOT NULL," +
                    "Losses INTEGER NOT NULL," +
                    "PRIMARY KEY(SID)," +
                    "FOREIGN KEY(Username) REFERENCES User(Username)" +
                    ")"
                    ;

            sm.executeUpdate(sqlSM);
            db.closeConnection(true);
        }
        catch (DataAccessException e) {
            db.closeConnection(false);
            throw e;
        }
        catch (SQLException e) {
            db.closeConnection(false);
            e.printStackTrace();
            throw new DataAccessException("SQL Error Encountered while creating Stats Table");
        }
    }

    public void clearStatsTable() throws DataAccessException {
        Connection conn = db.openConnection();

        try (Statement sm = conn.createStatement()) {
            String sqlSM = "DELETE FROM Stats";

            sm.executeUpdate(sqlSM);
            db.closeConnection(true);
        }
        catch (DataAccessException e) {
            db.closeConnection(false);
            throw e;
        }
        catch (SQLException e) {
            db.closeConnection(false);
            throw new DataAccessException("SQL Error Encountered while clearing Stats Table");
        }
    }
}
