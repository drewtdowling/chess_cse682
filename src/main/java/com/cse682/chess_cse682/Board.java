package com.cse682.chess_cse682;

import com.cse682.chess_cse682.piece.King;
import com.cse682.chess_cse682.piece.Pawn;
import com.cse682.chess_cse682.piece.Piece;
import javafx.scene.layout.GridPane;

import java.io.Serializable;
import java.util.*;

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
        resetAttackedSquares();
    }

    public Square getSquare(int col, int row) {
        if (col < 0 || col > 7)
            return null;
        if (row < 0 || row > 7)
            return null;
        return squares[(row * 8) + col];
    }

    public King getKing(Color color) {
        for (Square square : squares) {
            if (square.getPiece() instanceof King && square.getPiece().getColor() == color) {
                return (King) square.getPiece();
            }
        }
        return null;
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

    public List<Piece> getPieces(Color color) {
        List<Piece> pieces = new ArrayList<>();
        Arrays.stream(squares)
                .filter(s -> s.getPiece() != null && (color == null || s.getPiece().getColor() == color))
                .forEach(s -> pieces.add(s.getPiece()));
        return pieces;
    }

    public void recalculateAttackedSquares() {
        resetAttackedSquares();
        Arrays.stream(squares).filter(s -> s.getPiece() != null && s.getPiece().getColor() == Color.WHITE)
                              .forEach(s -> attackedSquares.get(Color.WHITE).addAll(s.getPiece().computeAvailableSquares(false)));
        Arrays.stream(squares).filter(s -> s.getPiece() != null && s.getPiece().getColor() == Color.BLACK)
                .forEach(s -> attackedSquares.get(Color.BLACK).addAll(s.getPiece().computeAvailableSquares(false)));
    }
}
