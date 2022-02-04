package com.cse682.chess_cse682;

import javafx.scene.layout.GridPane;

/**
 * Class used to represent a chess board.
 */
public class Board extends GridPane {

    /**
     * Dimension of a chess board.
     */
    public static final int DIMENSION = 8;

    /**
     * Array of squares composing the chessboard.
     */
    private final Square[] squares = new Square[DIMENSION * DIMENSION];

    private int turn;

    /**
     * Default constructor for the Board class; initializes the squares
     * that compose the board along with their data.
     */
    Board() {
        for(int i = 0; i < DIMENSION * DIMENSION; i++) {
            Square square = new Square(this, i % 8, (i - (i % 8)) / 8);
            add(square, square.getRow(), square.getColumn());
            squares[i] = square;
        }
    }

    public Square getSquare(int col, int row) {
        if (col < 0 || col > 7)
            return null;
        if (row < 0 || row > 7)
            return null;
        return squares[(row * 8) + col];
    }

    public int currentTurn() {
        return this.turn;
    }

}
