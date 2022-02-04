package com.cse682.chess_cse682.piece;

import com.cse682.chess_cse682.Color;
import com.cse682.chess_cse682.Square;

import java.util.List;

public abstract class Piece {

    protected Square square;
    protected Color color;
    private String name;
    private String resourceFileName;
    private int firstTurnMoved;
    private boolean moved;
    protected int row;
    protected int col;

    Piece(Color color, String name, Square square) {
        this.setColor(color);
        this.setName(name);
        this.setSquare(square);
        this.setRow(square.getRow());
        this.setColumn(square.getColumn());
        this.setFirstTurnMoved(-1);
        this.setMoved(false);
        StringBuilder sb = new StringBuilder();
        sb.append("resources/images/")
          .append(this.name)
          .append("-")
          .append(this.color.toString())
          .append(".png");
        this.setResourceFileName(sb.toString());
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


}
