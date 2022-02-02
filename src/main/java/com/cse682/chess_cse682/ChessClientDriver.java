package com.cse682.chess_cse682;

import javafx.application.Application;

/**
 * Driver for the CSE 682 Chess application.  Simply launches the
 * application externally using the JavaFX api.
 */
public class ChessClientDriver {

    /**
     * Main method of execution for the CSE 682 Chess application.
     * @param args Command line arguments (none expected).
     */
    public static void main(String[] args) {
        Application.launch(ChessClientApplication.class, args);
    }

}
