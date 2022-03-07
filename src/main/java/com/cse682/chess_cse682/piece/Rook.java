package com.cse682.chess_cse682.piece;

import com.cse682.chess_cse682.Color;
import com.cse682.chess_cse682.Square;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {

    public Rook(Color color, Square square) {
        super(color, "rook", square);
    }

    @Override
    public List<Square> computeAvailableSquares() {
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
