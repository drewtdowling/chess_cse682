package com.cse682.chess_cse682.piece;

import com.cse682.chess_cse682.Color;
import com.cse682.chess_cse682.Square;

import java.util.ArrayList;
import java.util.List;

public class Queen extends Piece {

    public Queen(Color color, Square square) {
        super(color, "queen", square);
    }


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
