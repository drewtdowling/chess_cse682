package com.cse682.chess_cse682.piece;

import com.cse682.chess_cse682.Color;
import com.cse682.chess_cse682.Square;

import java.util.ArrayList;
import java.util.List;

/**
 * The Queen class represents a queen piece in the game of chess.
 */
public class Queen extends Piece {

    /**
     * Parameterized constructor for the Queen class.
     * @param color Color of the queen.
     * @param square Square where the queen is positioned.
     */
    public Queen(Color color, Square square) {
        super(color, "queen", square);
    }


    /**
     * Computes the collection of squares that the queen can move to.
     * @param includeNonAttackedSquares Boolean flag indicating if non-attacked squares should be included (only
     *                                  the case for {@link Pawn} pieces.
     * @return Collection of {@link Square} instances representing squares the queen can move to.
     */
    @Override
    public List<Square> computeAvailableSquares(boolean includeNonAttackedSquares) {
        List<Square> fields = new ArrayList<>();
        for (int i = 1; col + i < 8 && row + i < 8; i++)
            if (addSquare(col + i, row + i, fields))
                break;
        for (int i = 1; col + i < 8 && row - i >= 0; i++)
            if (addSquare(col + i, row - i, fields))
                break;
        for (int i = 1; col - i >= 0 && row + i < 8; i++)
            if (addSquare(col - i, row + i, fields))
                break;
        for (int i = 1; col - i >= 0 && row - i >= 0; i++)
            if (addSquare(col - i, row - i, fields))
                break;
        for (int i = 1; col + i < 8; i++)
            if (addSquare(col + i, row, fields))
                break;
        for (int i = 1; col - i >= 0; i++)
            if (addSquare(col - i, row, fields))
                break;
        for (int i = 1; row + i < 8; i++)
            if (addSquare(col, row + i, fields))
                break;
        for (int i = 1; row - i >= 0; i++)
            if (addSquare(col, row - i, fields))
                break;
        return fields;
    }

    /**
     * Utility method used in the outward exploration from the queen's
     * current position.
     * @param col Column value [0,7] left-to-right of the queen's position.
     * @param row Row value [0,7] top-to-bottom of the queen's position.
     * @param squares Collection of squares where the queen can move.
     * @return Flag indicating if the queen can move to the examined square.
     */
    private boolean addSquare(int col, int row, List<Square> squares) {
        Square square = this.square.getBoard().getSquare(col, row);
        if (square != null) {
            if (square.getPiece() == null) {
                squares.add(square);
                return false;
            } else if (square.getPiece().color != color) {
                squares.add(square);
            }
        }
        return true;
    }
}
