package com.cse682.chess_cse682.piece;

import com.cse682.chess_cse682.Color;
import com.cse682.chess_cse682.Square;

import java.util.ArrayList;
import java.util.List;

/**
 * The Rook class represents a rook piece in the game of chess.
 */
public class Rook extends Piece {

    /**
     * Parameterized constructor for the Rook class.
     * @param color Color of the rook piece.
     * @param square Location of the rook piece.
     */
    public Rook(Color color, Square square) {
        super(color, "rook", square);
    }

    /**
     * Compute a collection of {@link Square} instances where the rook can move.
     * @param includeNonAttackedSquares Boolean flag indicating if non-attacked squares should be included (only
     *                                  the case for {@link Pawn} pieces.
     * @return Collection of squares where the rook can move.
     */
    @Override
    public List<Square> computeAvailableSquares(boolean includeNonAttackedSquares) {
        List<Square> squares = new ArrayList<>();
        for (int i = 1; col + i < 8; i++) {
            if (addSquare(col + i, row, squares))
                break;
        }
        for (int i = 1; col - i >= 0; i++) {
            if (addSquare(col - i, row, squares))
                break;
        }
        for (int i = 1; row + i < 8; i++) {
            if (addSquare(col, row + i, squares))
                break;
        }
        for (int i = 1; row - i >= 0; i++) {
            if (addSquare(col, row - i, squares))
                break;
        }
        return squares;
    }

    /**
     * Determine if the rook can move to a specific square.
     * @param col Column value [0,7] left-to-right of the rook piece.
     * @param row Row value [0,7] top-to-bottom of the rook piece.
     * @param squares Collection of squares where the rook can move.
     * @return Boolean flag indicating if the rook could successfully move
     *         to the investigated square.
     */
    private boolean addSquare(int col, int row, List<Square> squares) {
        Square square = this.square.getBoard().getSquare(col, row);
        if (square.getPiece() == null) {
            squares.add(square);
            return false;
        } else if (square.getPiece().color != color) {
            squares.add(square);
        }
        return true;
    }
}
