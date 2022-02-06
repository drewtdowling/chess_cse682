package com.cse682.chess_cse682.piece;

import com.cse682.chess_cse682.Color;
import com.cse682.chess_cse682.Square;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {

    public Pawn(Color color, Square square) {
        super(color, "pawn", square);
    }

    @Override
    public List<Square> computeAvailableSquares() {
        List<Square> squares = new ArrayList<>();
        Square square;
        // Check for legal moves for a white pawn.
        if (this.color == Color.WHITE) {

            // Check if the square directly in front of the pawn is available.
            if ((square = this.square.getBoard().getSquare(col, row - 1)) != null && square.getPiece() == null) {
                squares.add(square);
                // If the pawn is in the starting row, check to see if the space
                // two spaces ahead is open.
                if (row == 6 && (square = this.square.getBoard().getSquare(col, row - 2)).getPiece() == null) {
                    squares.add(square);
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
            // Check to see if the square in front of the black pawn is open.
            if ((square = this.square.getBoard().getSquare(col, row + 1)) != null && square.getPiece() == null) {
                squares.add(square);
                if (row == 1 && (square = this.square.getBoard().getSquare(col, row + 2)).getPiece() == null) {
                    squares.add(square);
                }
            }

            // Check potential captures.
            // Check to see if the piece is at the edge of the board, if the space is occupied, and if the space is
            // occupied by an opponents piece.
            if (col - 1 >= 0 && (square = this.square.getBoard().getSquare(col - 1, row + 1)).getPiece() != null
                             && square.getPiece().color != color) {
                squares.add(square);
            }
            if (col + 1 < 8 && (square = this.square.getBoard().getSquare(col + 1, row + 1)).getPiece() != null
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
}
