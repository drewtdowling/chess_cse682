package com.cse682.chess_cse682;

import com.cse682.chess_cse682.piece.Pawn;
import javafx.scene.layout.GridPane;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Class used to represent a chess board.
 */
public class Board extends GridPane {

    /**
     * Dimension of a chess board.
     */
    public static final int DIMENSION = 8;

    private ChessGame game;

    private Map<Color, Set<Square>> attackedSquares = new HashMap<>();

    /**
     * Array of squares composing the chessboard.
     */
    private final Square[] squares = new Square[DIMENSION * DIMENSION];

    /**
     * Default constructor for the Board class; initializes the squares
     * that compose the board along with their data.
     */
    Board(ChessGame game) {
        this.setGame(game);
        for(int i = 0; i < DIMENSION * DIMENSION; i++) {
            Square square = new Square(this, (i - (i % 8)) / 8, i % 8);
            add(square, square.getColumn(), square.getRow());
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

    private void setGame(ChessGame game) {
        if (game != null)
            this.game = game;
    }

    public ChessGame getGame() {
        return this.game;
    }

    private void resetAttackedSquares() {
        this.attackedSquares.put(Color.BLACK, new HashSet<>());
        this.attackedSquares.put(Color.WHITE, new HashSet<>());
    }

    public Set<Square> getAvailableSquares(Color color) {
        return this.attackedSquares.get(color);
    }

}
