package com.cse682.chess_cse682.piece;

import com.cse682.chess_cse682.Color;
import com.cse682.chess_cse682.Square;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * This class represents the king piece in the chess game.  It is the principal piece that the
 * player must protect during the game.
 */
public class King extends Piece {

    /**
     * Parameterized constructor for the King class.
     * @param color Color of the king piece.
     * @param square Square on which the king is placed.
     */
    public King(Color color, Square square) {
        super(color, "king", square);
    }

    /**
     * Computes the squares to which the king is able to move.
     * @param includeNonAttackedSquares Unused parameter; kings attack all squares they can move to.
     * @return List of squares the king is able to move into.
     */
    @Override
    public List<Square> computeAvailableSquares(boolean includeNonAttackedSquares) {
        List<Square> squares = new ArrayList<>();

        // Acquire the set of all squares currently attacked by the opponent.
        Set<Square> attackedSquares = square.getBoard().getAvailableSquares(color.opposite());

        // Explore the squares in the immediate vicinity of the king to see if the king can move
        // there, and if so, add the square to the list of squares.
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

    /**
     * Check if the king is "in check," meaning check if it is attacked.
     * @return Boolean flag indicating if the king is attacked.
     */
    public boolean inCheck() {
        return square.getBoard().getAvailableSquares(color.opposite()).contains(square);
    }

    /**
     * Check if the king is "in checkmate," meaning the king is attacked, and there are no
     * moves the player can perform that result in the king no longer being in check.
     * @return Boolean flag indicating if the king is in checkmate.
     */
    public boolean inCheckmate() {
        if (inCheck()) {
            for (Piece piece : square.getBoard().getPieces(this.color)) {
                // Check if the piece has any acceptable moves; if yes, the game can continue
                // and the king is not in checkmate.
                if (piece.getAllAvailableSquares().size() > 0)
                    return false;
            }
        }
        return true;
    }

    /**
     * Check if the board has reached a stalemate position.  Stalemate means that the king is not in
     * check, but there is no move that the player can make that allows the game to continue.
     * @return Boolean flag indicating if the game has reached stalemate.
     */
    public boolean inStalemate() {
        if (!inCheck()) {
            for (Piece piece : square.getBoard().getPieces(this.color)) {
                List<Square> potentialMoves = piece.computeAvailableSquares(true);
                // For efficiency, computeAvailableSquares() does not take pins into account
                // when moving pieces, so we need to check for pinned pieces explicitly here
                // using the canMoveTo() method.
                for (Square square : potentialMoves) {
                    if (piece.canMoveTo(square)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Handler method executed after kings successfully move.  This handles relocating the rook after
     * a successful castle has been executed.
     * @param oldSquare Square from which the king moved.
     * @param newSquare Square to which the king moved.
     * @param graphic Boolean toggle for if the change should be visually reflected on the board.
     * @return Always returns null.
     */
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
