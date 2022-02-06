package com.cse682.chess_cse682.piece;

import com.cse682.chess_cse682.Color;
import com.cse682.chess_cse682.Square;

import java.util.List;

public class King extends Piece {

    public King(Color color, Square square) {
        super(color, "king", square);
    }

    @Override
    public List<Square> computeAvailableSquares() {
        return null;
    }
}
