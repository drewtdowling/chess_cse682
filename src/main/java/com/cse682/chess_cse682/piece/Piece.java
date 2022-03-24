package com.cse682.chess_cse682.piece;

import com.cse682.chess_cse682.ChessGame;
import com.cse682.chess_cse682.Color;
import com.cse682.chess_cse682.Square;
import javafx.scene.image.Image;
import javafx.scene.input.DataFormat;

import java.io.Serializable;
import java.util.*;

/**
 * The Piece class defines common private member variables and interfaces common to all
 * pieces in the chess game.
 */
public abstract class Piece implements Serializable {

    /**
     * DataFormat into which pieces are serialized.
     */
    public transient static final DataFormat CHESS_PIECE = new DataFormat("chess.piece");

    /**
     * Square where the piece is placed.
     */
    protected transient Square square;

    /**
     * Color of the piece.
     */
    protected Color color;

    /**
     * Name of the piece.
     */
    private String name;

    /**
     * Relative location of the image file for the piece in the file system.
     */
    private String resourceFileName;

    /**
     * Turn where the piece was first moved from its initial board position.
     */
    private int firstTurnMoved;

    /**
     * Boolean flag indicating if the piece has been moved.
     */
    private boolean moved;

    /**
     * Row value [0,7] top-to-bottom where the piece is positioned.
     */
    protected int row;

    /**
     * Column value [0,7] left-to-right where the piece is positioned.
     */
    protected int col;

    /**
     * Image cache that allows for assets to be loaded once per piece type and stored as Image instances
     * for later reference.
     */
    public transient static final Map<String, Image> pieceIconCache = new HashMap<>();

    /**
     * Parameterized constructor for the Piece class.
     * @param color Color of the piece.
     * @param name Name of the piece.
     * @param square Square upon which the piece is placed.
     */
    Piece(Color color, String name, Square square) {
        this.setColor(color);
        this.setName(name);
        this.setSquare(square);
        if (this.square != null) {
            this.setRow(square.getRow());
            this.setColumn(square.getColumn());
        }
        this.setFirstTurnMoved(-1);
        this.setMoved(false);

        // Build the string containing a relative path to the asset file for the piece, then
        // load the image and cache it for later.
        StringBuilder sb = new StringBuilder();
        sb.append(this.name)
          .append("-")
          .append(this.color.toString())
          .append(".png");
        this.setResourceFileName(sb.toString());
        if (!pieceIconCache.containsKey(this.resourceFileName)) {
            // This icon has not been loaded yet, so go ahead and do that now and cache it
            // for future use.
            Image icon = new Image(
                    Objects.requireNonNull(ChessGame.class.getClassLoader().getResourceAsStream(resourceFileName)),
                    50,
                    50,
                    true,
                    true);
            pieceIconCache.put(this.resourceFileName, icon);
        }
    }

    /**
     * Setter method for the color of the piece.
     * @param color Color of the piece.
     */
    private void setColor(Color color) {
        this.color = color;
    }

    /**
     * Getter method for the color of the piece.
     * @return Color of the piece.
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * Setter method for the name of the piece.
     * @param name Name of the piece.
     */
    private void setName(String name) {
        this.name = name;
    }

    /**
     * Getter method for the name of the piece.
     * @return Name of the piece.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Setter method for the square on which the piece is placed.
     * @param square Square upon which the piece is placed.
     */
    public void setSquare(Square square) {
        this.square = square;
    }

    /**
     * Getter method for the square on which the piece is placed.
     * @return Square on which the piece is placed.
     */
    public Square getSquare() {
        return this.square;
    }

    /**
     * Setter method for the first turn moved attribute of the Piece class.
     * @param firstTurnMoved Turn number when the piece was first moved.
     */
    protected void setFirstTurnMoved(int firstTurnMoved) {
        this.firstTurnMoved = firstTurnMoved;
    }

    /**
     * Getter method for the first turn moved attribute of the piece class.
     * @return Turn when the piece was first moved from its initial position.
     */
    public int getFirstTurnMoved() {
        return this.firstTurnMoved;
    }

    /**
     * Setter method for the moved flag of the piece class.
     * @param moved Boolean flag indicating if the piece has been moved.
     */
    private void setMoved(boolean moved) {
        this.moved = moved;
    }

    /**
     * Check if the piece has been moved from its initial board position.
     * @return Boolean flag indicating if the piece has been moved from its initial position.
     */
    public boolean hasBeenMoved() {
        return this.moved;
    }

    /**
     * Utility method to determine if a piece can be moved.
     * @return Boolean flag indicating if the piece can be moved.
     */
    public boolean canMove() {
        return this.square.getBoard().getGame().nextToMove() == this.color;
    }

    /**
     * Determine if a piece can move to a specific square.
     * @return Boolean flag indicating if the piece can be moved to the specified square.
     */
    public boolean canMoveTo(Square square) {
        return canMove() && getAllAvailableSquares().contains(square);
    }

    /**
     * Setter method for the row of the piece.
     * @param row Row value [0,7] top-to-bottom of the piece.
     */
    private void setRow(int row) {
        if (row < 0 || row > 7)
            throw new IllegalArgumentException("Cannot set invalid row value.");
        this.row = row;
    }

    /**
     * Getter method for the row of the piece.
     * @return Row value [0,7] top-to-bottom of the piece.
     */
    public int getRow() {
        return this.row;
    }

    /**
     * Setter method for the column of the piece.
     * @param col Column value [0,7] left-to-right where the piece is located.
     */
    private void setColumn(int col) {
        if (col < 0 || col > 7)
            throw new IllegalArgumentException("Cannot set invalid column value.");
        this.col = col;
    }

    /**
     * Getter method for the column of the piece.
     * @return Column value [0,7] left-to-right where the piece is located.
     */
    public int getColumn() {
        return this.col;
    }

    /**
     * Setter method for the file name resource of the piece.
     * @param resourceFileName Relative path to the resource file containing the image of the piece.
     */
    private void setResourceFileName(String resourceFileName) {
        if (resourceFileName == null)
            throw new IllegalArgumentException("Cannot set null resource file name.");
        this.resourceFileName = resourceFileName;
    }

    /**
     * Getter method for the resource file name of the piece.
     * @return String containing a relative path to the file containing the image of the piece.
     */
    public String getResourceFileName() {
        return this.resourceFileName;
    }

    /**
     * Utility method to retrieve the {@link Image} representation of the asset to show on the board.
     * @return {@link Image} handle for the asset to be shown for the piece.
     */
    public Image getImage() {
        return pieceIconCache.get(this.resourceFileName);
    }

    /**
     * Check the validity of moves along with determining if the king enters a checked position.
     * @return Collection of {@link Square} instances representing the squares a piece can move to.
     */
    public List<Square> getAllAvailableSquares() {
        List<Square> squares = computeAvailableSquares(true);
        King king = square.getBoard().getKing(this.color);
        if (king != null) {
            List<Square> realSquares = new ArrayList<>();
            Square oldSquare = square;
            for (Square destinationSquare : squares) {
                Piece killedPiece = move(destinationSquare, false);
                square.getBoard().recalculateAttackedSquares();
                
                // Now that the set of attacked squares has been updated, check to see if the
                // king is in check, if yes, then the move was invalid.
                boolean check = king.inCheck();
                if (!check) {
                    realSquares.add(destinationSquare);
                }
                
                // revert the move to leave the board unmodified.
                move(oldSquare, false);
                if (killedPiece != null) {
                    killedPiece.square.setPiece(killedPiece, false);
                }
                square.getBoard().recalculateAttackedSquares();
            }
            squares = realSquares;
        }
        return squares;
    }

    /**
     * Compute a collection of {@link Square} instances representing squares the piece can move into.
     * @param includeNonAttackedSquares Boolean flag indicating if non-attacked squares should be included (only
     *                                  the case for {@link Pawn} pieces.
     * @return Collection of {@link Square} instances representing squares where the piece can move.
     */
    public abstract List<Square> computeAvailableSquares(boolean includeNonAttackedSquares);

    /**
     * Move a piece on the board.
     * @param square Square to which the piece is moving.
     * @param graphic Boolean flag indicating if the move should be visually reflected on the board.
     * @return Reference to the last piece moved.
     */
    public Piece move(Square square, boolean graphic) {
        // Save off a reference to the piece previously located on the square.
        Piece killed = square.getPiece();

        // Place the moved piece onto the square.
        square.setPiece(this, graphic);

        // Disassociate killed pieces with any square.
        if (this.square != null) {
            this.square.setPiece(null, graphic);
        }

        // Save off the previous row and column of the piece.
        int preX = this.col;
        int preY = this.row;
        this.row = square.getRow();
        this.col = square.getColumn();
        if(graphic) {
            ChessGame.addMoveHistory(new ChessGame.Move(this, preY, preX, this.row, this.col, killed));
        }
        // move the piece.
        this.setSquare(square);

        Piece post = postMoveHandler(square.getBoard().getSquare(preX, preY), square, graphic);
        if (graphic && !this.hasBeenMoved()) {
            this.firstTurnMoved = square.getBoard().getGame().currentTurn();
            this.moved = true;
        }

        return killed == null ? post : killed;
    }

    /**
     * Handler method to be overridden by derived classes to prescribe behaviors for more specialized
     * move actions (e.g. castling, en-passant, pawn-promotion, etc.).
     * @param oldSquare Square from which the piece was moved.
     * @param newSquare Square to which the piece was moved.
     * @param graphic Boolean flag indicating if the change(s) should be reflected visually on the board.
     * @return Reference to the moved or killed piece.
     */
    public Piece postMoveHandler(Square oldSquare, Square newSquare, boolean graphic) {
        return null;
    }
}
