package com.cse682.chess_cse682;

/**
 * Enumerated type used to represent colors of {@link Square}s and Pieces
 * in the chess game.
 */
public enum Color {
    BLACK("black"),
    WHITE("white");

    /**
     * Name of the color.
     */
    private final String name;

    /**
     * Parameterized constructor of the Color class.
     * @param colorStr String representation of the color.
     */
    Color(String colorStr) {
        this.name = colorStr;
    }

    /**
     * Utility method to negate a color.
     * @return Opposite of the invoking color.
     */
    public Color opposite() {
        return switch (this) {
            case BLACK -> WHITE;
            case WHITE -> BLACK;
        };
    }

    /**
     * Convert a color to a String.
     * @return String representation of the color.
     */
    @Override
    public String toString() {
        return this.name;
    }

    /**
     * Utility method to provide a capitalized name of a color.
     * @return Capitalized name of a color.
     */
    public String prettyName() {
        return switch (this) {
            case BLACK -> "Black";
            case WHITE -> "White";
        };
    }
}
