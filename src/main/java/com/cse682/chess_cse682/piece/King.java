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
    public List<Square> computeAvailableSquares(boolean includeNonAttackedSquares) {
        List<Square> squares = new ArrayList<>();

        Set<Square> attackedSquares = square.getBoard().getAvailableSquares(color.opposite());
        for (int xOffset = -1; xOffset <= 1; xOffset++) {
            for (int yOffset = -1; yOffset <= 1; yOffset++) {
                Square s;
                if ((s = square.getBoard().getSquare(square.getColumn() + xOffset, square.getRow() + yOffset)) != null
                    && s != square
                    && !attackedSquares.contains(s)
                    && (s.getPiece() == null || (s.getPiece() != null && s.getPiece().getColor() != color))) {
                    squares.add(s);
                }
            }
        }

        // Check if castling is possible.
        if (!hasBeenMoved()) {
            // Check for left castling.
            Square rookSquare = this.square.getBoard().getSquare(0, this.row);
            if (rookSquare.getPiece() instanceof Rook && !rookSquare.getPiece().hasBeenMoved()) {
                boolean leftCastlingPossible = true;
                for (int i = this.col - 1; i > 0; i--) {
                    Square iter = this.square.getBoard().getSquare(i, this.row);
                    if (iter.getPiece() != null || i > 1 && attackedSquares.contains(iter)) {
                        leftCastlingPossible = false;
                        break;
                    }
                }
                if (leftCastlingPossible)
                    squares.add(square.getBoard().getSquare(2, this.row));
            }
            // Check for right castling.
            rookSquare = this.square.getBoard().getSquare(7, this.row);
            if (rookSquare.getPiece() instanceof Rook && !rookSquare.getPiece().hasBeenMoved()) {
                boolean rightCastlingPossible = true;
                for (int i = this.col + 1; i < 7; i++) {
                    Square iter = square.getBoard().getSquare(i, this.row);
                    if (iter.getPiece() != null || attackedSquares.contains(iter)) {
                        rightCastlingPossible = false;
                        break;
                    }
                }
                if (rightCastlingPossible)
                    squares.add(square.getBoard().getSquare(6, this.row));
            }
        }

        return squares;
    }

    public boolean inCheck() {
        return square.getBoard().getAvailableSquares(color.opposite()).contains(square);
    }

    public boolean inCheckmate() {
        if (inCheck()) {
            for (Piece piece : square.getBoard().getPieces(this.color)) {
                if (piece.getAllAvailableSquares().size() > 0)
                    return false;
            }
        }
        return true;
    }

    public boolean inStalemate() {
        if (!inCheck()) {
            for (Piece piece : square.getBoard().getPieces(this.color)) {
                if (piece.computeAvailableSquares(true).size() > 0)
                    return false;
            }
        }
        return true;
    }

    public Piece postMoveHandler(Square oldSquare, Square newSquare, boolean graphic) {
        if (graphic && !hasBeenMoved()) {
            if (this.col == 2) {
                square.getBoard().getSquare(0, this.row).getPiece().move(square.getBoard().getSquare(3, this.row), true);
            }
            else if (this.col == 6) {
                square.getBoard().getSquare(7, this.row).getPiece().move(square.getBoard().getSquare(5, this.row), true);
            }
        }
        return null;
    }
}
