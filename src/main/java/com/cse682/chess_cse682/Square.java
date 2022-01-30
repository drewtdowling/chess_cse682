package com.cse682.chess_cse682;

import javafx.geometry.Pos;
import javafx.scene.control.Label;

public class Square extends Label {

    // TODO: private Piece piece;
    private int row, column;
    private Board board;

    private static final String defaultBlackStyle = "-fx-background-color: darkgreen";
    private static final String defaultWhiteStyle = "-fx-background-color: white";

    Square(Board board, int row, int column) {
        this.setBoard(board);
        this.setRow(row);
        this.setColumn(column);
        this.setAlignment(Pos.CENTER);
        this.initializeBackgroundColor();
        setMinSize(50, 50);
        setMaxSize(50, 50);
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Board getBoard() {
        return this.board;
    }

    public void setRow(int row) {
        if(row < 0 || row >= Board.DIMENSION)
            throw new IllegalArgumentException("Row values must be [0,7].");
        this.row = row;
    }

    public final int getRow() {
        return this.row;
    }

    public void setColumn(int column) {
        if(column < 0 || column >= Board.DIMENSION)
            throw new IllegalArgumentException("Column values must be [0,7].");
        this.column = column;
    }

    public final int getColumn() {
        return this.column;
    }

    private Color getColor() {
        Color color;
        if((this.row % 2 == 0 && this.column % 2 == 0) ||
                (this.row % 2 == 1 && this.column % 2 == 1)) {
            color = Color.WHITE;
        } else {
            color = Color.BLACK;
        }
        return color;
    }

    private void initializeBackgroundColor() {
        String style = switch (this.getColor()) {
            case BLACK -> defaultBlackStyle;
            case WHITE -> defaultWhiteStyle;
            default -> throw new RuntimeException("Invalid color encountered.");
        };
        this.setStyle(style);
    }
}
