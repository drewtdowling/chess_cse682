package com.cse682.chess_cse682.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Database {

    // Private connection variable to the database
    private Connection dbConn;

    public Database() {

    }

    public Connection openConnection() throws DataAccessException {
        try {
            String dbName = "chess.sqlite3";
            String connectionURL = "jdbc:sqlite:" + dbName; // static name

            this.dbConn = DriverManager.getConnection(connectionURL);

            this.dbConn.setAutoCommit(false);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new DataAccessException("Unable to Open Connection to DB");
        }

        return dbConn;
    }

    public void closeConnection(boolean commit) throws DataAccessException {
        try {
            if (commit)
                this.dbConn.commit();
            else
                this.dbConn.rollback();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new DataAccessException("Unable to Close Connection to DB");
        }
    }

}
