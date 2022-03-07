package com.cse682.chess_cse682.piece;

import com.cse682.chess_cse682.ChessGame;
import com.cse682.chess_cse682.Color;
import com.cse682.chess_cse682.Square;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public abstract class Piece {

    protected Square square;
    protected Color color;
    private String name;
    private String resourceFileName;
    private int firstTurnMoved;
    private boolean moved;
    protected int row;
    protected int col;

    public static final Map<String, Image> pieceIconCache = new HashMap<>();

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

    private void setSquare(Square square) {
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

    public abstract List<Square> computeAvailableSquares();

    public Piece move(Square square, boolean graphic) {
        Piece killed = square.getPiece();
        square.setPiece(this, graphic);
        if (this.square != null) {
            this.square.setPiece(null, graphic);
        }
        int preX = this.row;
        int preY = this.col;
        this.row = square.getRow();
        this.col = square.getColumn();
        this.setSquare(square);

        Piece post = postMoveHandler(square.getBoard().getSquare(preX, preY), square, graphic);
        if (graphic && !this.hasBeenMoved()) {
            this.firstTurnMoved = square.getBoard().getGame().currentTurn();
        }

        return killed == null ? post : killed;
    }

    // Override this method to provide logic checking after each move.
    public Piece postMoveHandler(Square oldSquare, Square newSquare, boolean graphic) {
        return null;
    }
}
