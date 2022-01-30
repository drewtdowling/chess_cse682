package com.cse682.chess_cse682;

import javafx.scene.layout.GridPane;

public class Board extends GridPane {

    public static final int DIMENSION = 8;

    private final Square[] squares = new Square[DIMENSION * DIMENSION];

    Board() {
        for(int i = 0; i < DIMENSION * DIMENSION; i++) {
            Square square = new Square(this, i % 8, (i - (i % 8)) / 8);
            add(square, square.getRow(), square.getColumn());
            squares[i] = square;
        }
    }

}
