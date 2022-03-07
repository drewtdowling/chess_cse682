package com.cse682.chess_cse682.piece;

import com.cse682.chess_cse682.Color;
import com.cse682.chess_cse682.Square;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class King extends Piece {

    public King(Color color, Square square) {
        super(color, "king", square);
    }

    /**
     * TODO: The King is the most complicated piece on the board because of the
     * special logic it requires per the rules of the game.  I will program it
     * on a separate branch because I want its implementation to be unique from
     * how I did the other pieces.
     * @return List of available squares where the king can move.
     */
    @Override
    public List<Square> computeAvailableSquares() {
        List<Square> squares = new ArrayList<>();

        Set<Square> attackedSquares = square.getBoard().getAvailableSquares(color.opposite());
        return squares;
    }
}
