package com.cse682.chess_cse682.piece;

import com.cse682.chess_cse682.Color;
import com.cse682.chess_cse682.Square;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {

    public Knight(Color color, Square square) {
        super(color, "knight", square);
    }

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

    private void addSquare(int col, int row, List<Square> squares) {
        Square square = this.square.getBoard().getSquare(col, row);
        if (square != null && (square.getPiece() == null || square.getPiece().color != color))
            squares.add(square);
    }
}
