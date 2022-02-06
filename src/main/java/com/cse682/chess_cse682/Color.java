package com.cse682.chess_cse682;

/**
 * Enumerated type used to represent colors of {@link Square}s and Pieces
 * in the chess game.
 */
public enum Color {
    BLACK("black"),
    WHITE("white");

    private String name;

    Color(String colorStr) {
        this.name = colorStr;
    }

    public Color opposite() {
        Color toReturn = switch (this) {
            case BLACK -> WHITE;
            case WHITE -> BLACK;
            default -> throw new IllegalStateException("Unexpected value: " + this);
        };
        return toReturn;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
