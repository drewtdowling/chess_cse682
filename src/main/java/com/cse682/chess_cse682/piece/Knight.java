package com.cse682.chess_cse682.piece;

import com.cse682.chess_cse682.Color;
import com.cse682.chess_cse682.Square;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a knight piece in a game of chess.
 */
public class Knight extends Piece {

    /**
     * Parameterized constructor for the Knight class.
     * @param color Color of the knight piece.
     * @param square Square on which the knight is placed.
     */
    public Knight(Color color, Square square) {
        super(color, "knight", square);
    }

    /**
     * Compute all squares where the knight can successfully move.
     * @param includeNonAttackedSquares Unused parameter; knights attack all squares they can move to.
     * @return Collection of {@link Square} instances representing the squares where the knight can move.
     */
    @Override
    public List<Square> computeAvailableSquares(boolean includeNonAttackedSquares) {
        List<Square> squares = new ArrayList<>();
        addSquare(col + 2, row + 1, squares);
        addSquare(col + 2, row - 1, squares);
        addSquare(col + 1, row + 2, squares);
        addSquare(col + 1, row - 2, squares);
        addSquare(col - 2, row + 1, squares);
        addSquare(col - 2, row - 1, squares);
        addSquare(col - 1, row + 2, squares);
        addSquare(col - 1, row - 2, squares);
        return squares;
    }

    /**
     * Utility method used in the screening of squares to see if the knight can move there.
     * @param col Column [0,7] left-to-right of the square being considered.
     * @param row Row [0,7] top-to-bottom of the square being considered.
     * @param squares Collection of squares where the knight can successfully move.
     */
    private void addSquare(int col, int row, List<Square> squares) {
        Square square = this.square.getBoard().getSquare(col, row);
        if (square != null && (square.getPiece() == null || square.getPiece().color != color))
            squares.add(square);
    }
}
