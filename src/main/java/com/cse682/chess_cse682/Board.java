package com.cse682.chess_cse682;

import com.cse682.chess_cse682.piece.King;
import com.cse682.chess_cse682.piece.Pawn;
import com.cse682.chess_cse682.piece.Piece;
import javafx.scene.layout.GridPane;

import java.io.Serializable;
import java.util.*;

/**
 * The Board class represents a board in a chess game.
 */
public class Board extends GridPane {

    /**
     * Dimension of a chess board.
     */
    public static final int DIMENSION = 8;

    /**
     * Reference to the current {@link ChessGame} played on this board.
     */
    private ChessGame game;

    /**
     * Map identifying all squares on the board attacked by each player
     * by their respective piece color.
     */
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

    /**
     * Retrieve a {@link Square} from within the board.
     * @param col Column value [0,7] left-to-right of the {@link Square}.
     * @param row Row value [0,7] top-to-bottom of the {@link Square}.
     * @return Square within the board, or null if invalid arguments.
     */
    public Square getSquare(int col, int row) {
        if (col < 0 || col > 7)
            return null;
        if (row < 0 || row > 7)
            return null;
        return squares[(row * 8) + col];
    }

    /**
     * Retrieves the {@link King} of the specified color.
     * @param color Color of the {@link King} to retrieve.
     * @return {@link King} piece of the specified color.
     */
    public King getKing(Color color) {
        for (Square square : squares) {
            if (square.getPiece() instanceof King && square.getPiece().getColor() == color) {
                return (King) square.getPiece();
            }
        }
        return null;
    }

    /**
     * Setter method for the current game played on the {@link Board}.
     * @param game {@link ChessGame} currently played on the {@link Board}.
     */
    private void setGame(ChessGame game) {
        if (game != null)
            this.game = game;
    }

    /**
     * Getter method for the current game played on the {@link Board}.
     * @return {@link ChessGame} currently played on the {@link Board}.
     */
    public ChessGame getGame() {
        return this.game;
    }

    /**
     * Utility method to reset the data structure tracking the attacked squares.
     */
    private void resetAttackedSquares() {
        this.attackedSquares.put(Color.BLACK, new HashSet<>());
        this.attackedSquares.put(Color.WHITE, new HashSet<>());
    }

    /**
     * Utility method to compute the set of available squares for a given piece.
     * @param color Color for which the available squares should be retrieved.
     * @return Collection of available squares.
     */
    public Set<Square> getAvailableSquares(Color color) {
        return this.attackedSquares.get(color);
    }

    /**
     * Utility method to get all pieces of a specified color.
     * @param color Color of the pieces to retrieve.
     * @return Collection of pieces of the specified color.
     */
    public List<Piece> getPieces(Color color) {
        List<Piece> pieces = new ArrayList<>();
        Arrays.stream(squares)
                .filter(s -> s.getPiece() != null && (color == null || s.getPiece().getColor() == color))
                .forEach(s -> pieces.add(s.getPiece()));
        return pieces;
    }

    /**
     * Utility method to recompute both sets of {@link Square} attacked by both players.
     */
    public void recalculateAttackedSquares() {
        resetAttackedSquares();
        Arrays.stream(squares).filter(s -> s.getPiece() != null && s.getPiece().getColor() == Color.WHITE)
                              .forEach(s -> attackedSquares.get(Color.WHITE).addAll(s.getPiece().computeAvailableSquares(false)));
        Arrays.stream(squares).filter(s -> s.getPiece() != null && s.getPiece().getColor() == Color.BLACK)
                .forEach(s -> attackedSquares.get(Color.BLACK).addAll(s.getPiece().computeAvailableSquares(false)));
    }
}
