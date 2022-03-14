package com.cse682.chess_cse682.piece;

import com.cse682.chess_cse682.Color;
import com.cse682.chess_cse682.Square;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the Bishop chess piece, which can move diagonally in any direction.
 */
public class Bishop extends Piece {

    /**
     * Parameterized constructor for the bishop.
     * @param color Color of the bishop piece.
     * @param square Square upon which the bishop is placed.
     */
    public Bishop(Color color, Square square) {
        super(color, "bishop", square);
    }

    /**
     * Compute all possible squares the bishop can move to.
     * @param includeNonAttackedSquares Unused parameter; bishops attack all squares they can move to.
     * @return List of {@link Square} instances the bishop can move to.
     */
    @Override
    public List<Square> computeAvailableSquares(boolean includeNonAttackedSquares) {
        List<Square> squares = new ArrayList<>();
        for (int i = 1; col + i < 8 && row + i < 8; i++)
            if (addSquare(col + i, row + i, squares))
                break;
        for (int i = 1; col + i < 8 && row - i >= 0; i++)
            if (addSquare(col + i, row - i, squares))
                break;
        for (int i = 1; col - i >= 0 && row + i < 8; i++)
            if (addSquare(col - i, row + i, squares))
                break;
        for (int i = 1; col - i >= 0 && row - i >= 0; i++)
            if (addSquare(col - i, row - i, squares))
                break;
        return squares;
    }

    /**
     * Utility method used in exploring available squares for the bishop to move into.
     * @param col Column [0,7] left-to-right of the square being considered.
     * @param row Row [0,7] top-to-bottom of the square being considered.
     * @param squares Collection of valid squares the bishop can move into.
     * @return Boolean flag indicating if the bishop can successfully move to the square.
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
