package com.cse682.chess_cse682;

import javafx.geometry.Pos;
import javafx.scene.control.Label;

/**
 * Represents one square on a chess board.
 */
public class Square extends Label {

    // TODO: Implement a Piece class along with sub-classes for the various chess pieces.
    // private Piece piece;
    private int row, column;
    private Board board;

    /**
     * Dark-green color used for the dark squares on the board.
     */
    private static final String defaultBlackStyle = "-fx-background-color: darkgreen";

    /**
     * Light gray color used for the white squares on the board.
     */
    private static final String defaultWhiteStyle = "-fx-background-color: lightgray";

    /**
     * Parameterized constructor for the {@link Square} class.
     * @param board {@link Board} that will contain this square.
     * @param row Row into which this square will be placed.
     * @param column Column into which this square will be placed.
     */
    Square(Board board, int row, int column) {
        this.setBoard(board);
        this.setRow(row);
        this.setColumn(column);
        this.setAlignment(Pos.CENTER);
        this.initializeBackgroundColor();
        setMinSize(50, 50);
        setMaxSize(50, 50);
    }

    /**
     * Setter method for the reference to the parent board.
     * @param board Board containing this square.
     */
    public void setBoard(Board board) {
        this.board = board;
    }

    /**
     * Getter method for the reference to the parent board.
     * @return Board in which this Square is placed.
     */
    public Board getBoard() {
        return this.board;
    }

    /**
     * Setter method for the row of the Square.
     * @param row Row value [0,7] indicating the position of the Square within the {@link Board}.
     */
    public void setRow(int row) {
        if(row < 0 || row >= Board.DIMENSION)
            throw new IllegalArgumentException("Row values must be [0,7].");
        this.row = row;
    }

    /**
     * Getter method for the row of the {@link Square}.
     * @return Row value [0,7] indicating the position of the Square within the {@link Board}.
     */
    public final int getRow() {
        return this.row;
    }

    /**
     * Setter method for the column of the {@link Square}.
     * @param column Column value [0,7] indicating the position of the {@link Square} within the {@link Board}.
     */
    public void setColumn(int column) {
        if(column < 0 || column >= Board.DIMENSION)
            throw new IllegalArgumentException("Column values must be [0,7].");
        this.column = column;
    }

    /**
     * Getter method for the column of the {@link Square}
     * @return Column value [0,7] indicating hte position of the {@link Square} within the {@link Board}.
     */
    public final int getColumn() {
        return this.column;
    }

    /**
     * Determines the color of the square from its position in the {@link Board}.
     * @return {@link Color} enumeration indicating the color of the {@link Square}.
     */
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

    /**
     * Initializes the color of the square.
     */
    private void initializeBackgroundColor() {
        String style = switch (this.getColor()) {
            case BLACK -> defaultBlackStyle;
            case WHITE -> defaultWhiteStyle;
            default -> throw new RuntimeException("Invalid color encountered.");
        };
        this.setStyle(style);
    }
}
