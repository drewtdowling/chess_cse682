package com.cse682.chess_cse682.db;

public class DataAccessException extends Exception {

    /**
     * Default constructor for DAE
     * */
    DataAccessException() {
        super();
    }

    /**
     * Constructor for DAE messages
     * @param msg - displays when an exception is thrown
     * */
    DataAccessException(String msg) {
        super(msg);
    }
}
