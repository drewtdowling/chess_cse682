package com.cse682.chess_cse682.piece;

import com.cse682.chess_cse682.Color;
import com.cse682.chess_cse682.PromotionDialog;
import com.cse682.chess_cse682.Square;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * This class represents a pawn piece in a game of chess.
 */
public class Pawn extends Piece {

    /**
     * Parameterized constructor for the Pawn class.
     * @param color Color of the pawn.
     * @param square Square on which the pawn is placed.
     */
    public Pawn(Color color, Square square) {
        super(color, "pawn", square);
    }

    /**
     * Compute the collection of squares where a pawn can successfully move.
     * @param includeNonAttackedSquares Boolean flag indicating if the spaces directly in front of the pawn should be
     *                                  included.
     * @return Collection of squares where the pawn can successfully move.
     */
    @Override
    public List<Square> computeAvailableSquares(boolean includeNonAttackedSquares) {
        List<Square> squares = new ArrayList<>();
        Square square;
        // Check for legal moves for a white pawn.
        if (this.color == Color.WHITE) {

            // Check for squares located directly in front of the pawn.
            if (includeNonAttackedSquares) {
                // Check if the square directly in front of the pawn is available.
                if ((square = this.square.getBoard().getSquare(col, row - 1)) != null && square.getPiece() == null) {
                    squares.add(square);
                    // If the pawn is in the starting row, check to see if the space
                    // two spaces ahead is open.
                    if (row == 6 && (square = this.square.getBoard().getSquare(col, row - 2)).getPiece() == null) {
                        squares.add(square);
                    }
                }
            }

            // Check potential captures of opponents pieces.
            // Check to see if the piece is at the edge of the board, if the space is occupied,
            // and if the space is occupied by an opponent's piece.
            if (col - 1 >= 0 && (square = this.square.getBoard().getSquare(col - 1, row - 1)) != null
                    && square.getPiece() != null && square.getPiece().color != color) {
                squares.add(square);
            }
            if (col + 1 < 8 && (square = this.square.getBoard().getSquare(col + 1, row - 1)) != null
                            && square.getPiece() != null && square.getPiece().getColor() != color) {
                squares.add(square);
            }

            // Check to see if an opponent's pawn can be taken using the en-passant rule.
            // En-passant requires that the opponents pawn be moved the previous turn, and
            // that it moved two spaces forward, which it can only do on its first turn.
            if (row == 3) {
                if (col - 1 >= 0 && (square = this.square.getBoard().getSquare(col - 1, row)).getPiece() != null
                                 && square.getPiece().color != color
                                 && square.getPiece() instanceof Pawn
                                 && square.getBoard().getGame().currentTurn() - square.getPiece().getFirstTurnMoved() == 1) {
                    squares.add(square.getBoard().getSquare(col - 1, row - 1));
                }
                if (col + 1 < 8 && (square = this.square.getBoard().getSquare(col + 1, row)).getPiece() != null
                                && square.getPiece().color != color
                                && square.getPiece() instanceof Pawn
                                && square.getBoard().getGame().currentTurn() - square.getPiece().getFirstTurnMoved() == 1) {
                    squares.add(square.getBoard().getSquare(col + 1, row - 1));
                }
            }
        }
        // check for legal moves for a black pawn.
        else {

            // Check for squares located directly in front of the pawn.
            if (includeNonAttackedSquares) {
                // Check to see if the square in front of the black pawn is open.
                if ((square = this.square.getBoard().getSquare(col, row + 1)) != null && square.getPiece() == null) {
                    squares.add(square);
                    if (row == 1 && (square = this.square.getBoard().getSquare(col, row + 2)).getPiece() == null) {
                        squares.add(square);
                    }
                }
            }

            // Check potential captures.
            // Check to see if the piece is at the edge of the board, if the space is occupied, and if the space is
            // occupied by an opponents piece.
            if (col - 1 >= 0 && (square = this.square.getBoard().getSquare(col - 1, row + 1)) != null && square.getPiece() != null
                             && square.getPiece().color != color) {
                squares.add(square);
            }
            if (col + 1 < 8 && (square = this.square.getBoard().getSquare(col + 1, row + 1)) != null && square.getPiece() != null
                            && square.getPiece().color != color) {
                squares.add(square);
            }

            // Check for en-passant captures.
            if (row == 4) {
                if (col - 1 >= 0 && (square = this.square.getBoard().getSquare(col - 1, row)).getPiece() != null
                                 && square.getPiece().color != color
                                 && square.getPiece() instanceof Pawn
                                 && square.getBoard().getGame().currentTurn() - square.getPiece().getFirstTurnMoved() == 1) {
                    squares.add(square);
                }
                if (col + 1 < 8 && (square = this.square.getBoard().getSquare(col + 1, row)).getPiece() != null
                                && square.getPiece().color != color
                                && square.getPiece() instanceof Pawn
                                && square.getBoard().getGame().currentTurn() - square.getPiece().getFirstTurnMoved() == 1) {
                    squares.add(square);
                }
            }
        }
        return squares;
    }

    /**
     * Handler method executed after pawns are successfully moved to perform follow-on actions.  In the case of the
     * pawn, this means either capturing an opponent's pawn taken en-passant or initiating the pawn promotion dialog.
     * @param oldSquare Square from which the pawn moved.
     * @param newSquare Square to which the pawn moved.
     * @param graphic Boolean flag indicating if the change should be reflected visually in the GUI.
     * @return Piece the captured the opponents pawn en-passant, or null.
     */
    @Override
    public Piece postMoveHandler(Square oldSquare, Square newSquare, boolean graphic) {
        Piece pawn = null;

        // Check if a piece was captured en-passant and if so, remove the killed pawn.
        if (color == Color.BLACK && square.getRow() == 5
                    && (pawn = square.getBoard().getSquare(this.col, 4).getPiece()) != null
                    && pawn instanceof Pawn
                    && square.getBoard().getGame().currentTurn() - pawn.getFirstTurnMoved() == 1) {
            pawn.getSquare().setPiece(null, graphic);
        }
        if (color == Color.WHITE && square.getRow() == 2
                    && (pawn = square.getBoard().getSquare(this.col, 3).getPiece()) != null
                    && pawn instanceof Pawn
                    && square.getBoard().getGame().currentTurn() - pawn.getFirstTurnMoved() == 1) {
            pawn.getSquare().setPiece(null, graphic);
        }

        // Check to see if the pawn promotion dialog should be issued to the player.
        if (graphic) {
            if (square.getRow() == 0 || square.getRow() == 7) {
                Optional<Piece> result = new PromotionDialog(this).showAndWait();
                result.ifPresent(f -> f.move(square, true));
            }
        }

        return pawn;
    }
}
