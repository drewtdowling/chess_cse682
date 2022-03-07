package com.cse682.chess_cse682.piece;

import com.cse682.chess_cse682.ChessGame;
import com.cse682.chess_cse682.Color;
import com.cse682.chess_cse682.Square;
import javafx.scene.image.Image;
import javafx.scene.input.DataFormat;

import java.io.Serializable;
import java.util.*;

public abstract class Piece implements Serializable {

    public transient static final DataFormat CHESS_PIECE = new DataFormat("chess.piece");

    protected Square square;
    protected Color color;
    private String name;
    private String resourceFileName;
    private int firstTurnMoved;
    private boolean moved;
    protected int row;
    protected int col;

    public transient static final Map<String, Image> pieceIconCache = new HashMap<>();

    Piece(Color color, String name, Square square) {
        this.setColor(color);
        this.setName(name);
        this.setSquare(square);
        this.setRow(square.getRow());
        this.setColumn(square.getColumn());
        this.setFirstTurnMoved(-1);
        this.setMoved(false);
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

    private void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }

    private void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setSquare(Square square) {
        if (square == null)
            throw new IllegalArgumentException("Cannot set null Square.");
        this.square = square;
    }

    public Square getSquare() {
        return this.square;
    }

    protected void setFirstTurnMoved(int firstTurnMoved) {
        this.firstTurnMoved = firstTurnMoved;
    }

    public int getFirstTurnMoved() {
        return this.firstTurnMoved;
    }

    private void setMoved(boolean moved) {
        this.moved = moved;
    }

    public boolean canMove() {
        return this.square.getBoard().getGame().nextToMove() == this.color;
    }

    public boolean canMoveTo(Square square) {
        return canMove() && getAllAvailableSquares().contains(square);
    }

    public boolean hasBeenMoved() {
        return this.moved;
    }

    private void setRow(int row) {
        if (row < 0 || row > 7)
            throw new IllegalArgumentException("Cannot set invalid row value.");
        this.row = row;
    }

    public int getRow() {
        return this.row;
    }

    private void setColumn(int col) {
        if (col < 0 || col > 7)
            throw new IllegalArgumentException("Cannot set invalid column value.");
        this.col = col;
    }

    public int getColumn() {
        return this.col;
    }

    private void setResourceFileName(String resourceFileName) {
        if (resourceFileName == null)
            throw new IllegalArgumentException("Cannot set null resource file name.");
        this.resourceFileName = resourceFileName;
    }

    public String getResourceFileName() {
        return this.resourceFileName;
    }

    public Image getImage() {
        return pieceIconCache.get(this.resourceFileName);
    }

    public List<Square> getAllAvailableSquares() {
        List<Square> squares = computeAvailableSquares();
        King king = square.getBoard().getKing(this.color);
        if (king != null) {
            List<Square> trueSquares = new ArrayList<>();
            Square oldSquare = square;
            for (Square destinationSquare : squares) {
                Piece killedPiece = move(destinationSquare, false);
                square.getBoard().recalculateAttackedSquares();
                boolean check = king.inCheck();
                if (!check) {
                    trueSquares.add(destinationSquare);
                }
                // revert the move to leave the board unmodified.
                move(oldSquare, false);
                if (killedPiece != null) {
                    killedPiece.square.setPiece(killedPiece, false);
                }
                square.getBoard().recalculateAttackedSquares();
            }
            squares = trueSquares;
        }
        return squares;
    }

    public abstract List<Square> computeAvailableSquares();

    public Piece move(Square square, boolean graphic) {
        Piece killed = square.getPiece();
        square.setPiece(this, graphic);
        if (this.square != null) {
            this.square.setPiece(null, graphic);
        }

        int preX = this.col;
        int preY = this.row;
        this.row = square.getRow();
        this.col = square.getColumn();
        this.setSquare(square);

        Piece post = postMoveHandler(square.getBoard().getSquare(preX, preY), square, graphic);
        if (graphic && !this.hasBeenMoved()) {
            this.firstTurnMoved = square.getBoard().getGame().currentTurn();
            this.moved = true;
        }

        return killed == null ? post : killed;
    }

    // Override this method to provide logic checking after each move.
    public Piece postMoveHandler(Square oldSquare, Square newSquare, boolean graphic) {
        return null;
    }
}
