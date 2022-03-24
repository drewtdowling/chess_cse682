package com.cse682.chess_cse682;

import com.cse682.chess_cse682.piece.Pawn;
import com.cse682.chess_cse682.piece.Piece;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;

import java.util.List;

/**
 * Represents one square on a chess board.
 */
public class Square extends Label {

    // TODO: Implement a Piece class along with sub-classes for the various chess pieces.
    private Piece piece;
    private int row, column;
    private Board board;

    /**
     * Dark-green color used for the dark squares on the board.
     */
    private static final String defaultBlackStyle = "-fx-background-color: darkgreen";

    /**
     * Light gray color used for the white squares on the board.
     */
    private static final String defaultWhiteStyle = "-fx-background-color: lightgray";

    private static final String highlightedStyle = "-fx-background-color: rgb(0,177,0);" +
            "-fx-border-color: rgb(0,50,0);" + "-fx-border-width: 3;" + "-fx-border-insets: 1 1 1 1;";

    private static final int dragOffset;

    static {
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("windows")) {
            dragOffset = 25;
        } else {
            dragOffset = 0;
        }
    }

    /**
     * Parameterized constructor for the {@link Square} class.
     * @param board {@link Board} that will contain this square.
     * @param row Row into which this square will be placed.
     * @param column Column into which this square will be placed.
     */
    Square(Board board, int row, int column) {
        this.setBoard(board);
        this.setPiece(null);
        this.setRow(row);
        this.setColumn(column);
        this.setAlignment(Pos.CENTER);
        this.initializeBackgroundColor();
        setMinSize(50, 50);
        setMaxSize(50, 50);
        setOnMouseEntered(e -> onMouseEntered());
        setOnMouseExited(e -> onMouseExited());
        setOnDragDetected(this::onDragDetected);
        setOnDragOver(this::onDragOver);
        setOnDragDone(this::onDragDone);
        setOnDragDropped(this::onDragDropped);
    }

    /**
     * Setter method for the reference to the parent board.
     * @param board Board containing this square.
     */
    public void setBoard(Board board) {
        this.board = board;
    }

    /**
     * Getter method for the reference to the parent board.
     * @return Board in which this Square is placed.
     */
    public Board getBoard() {
        return this.board;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
        if(piece == null)
            setGraphic(null);
        else
            setGraphic(new ImageView(Piece.pieceIconCache.get(piece.getResourceFileName())));
    }

    public void setPiece(Piece piece, boolean graphic) {
        this.piece = piece;
        if (graphic) {
            if (piece == null) {
                setGraphic(null);
            } else {
                setGraphic(new ImageView(Piece.pieceIconCache.get(piece.getResourceFileName())));
            }
        }
    }

    public Piece getPiece() {
        return this.piece;
    }

    /**
     * Setter method for the row of the Square.
     * @param row Row value [0,7] indicating the position of the Square within the {@link Board}.
     */
    public void setRow(int row) {
        if(row < 0 || row >= Board.DIMENSION)
            throw new IllegalArgumentException("Row values must be [0,7].");
        this.row = row;
    }

    /**
     * Getter method for the row of the {@link Square}.
     * @return Row value [0,7] indicating the position of the Square within the {@link Board}.
     */
    public final int getRow() {
        return this.row;
    }

    /**
     * Setter method for the column of the {@link Square}.
     * @param column Column value [0,7] indicating the position of the {@link Square} within the {@link Board}.
     */
    public void setColumn(int column) {
        if(column < 0 || column >= Board.DIMENSION)
            throw new IllegalArgumentException("Column values must be [0,7].");
        this.column = column;
    }

    /**
     * Getter method for the column of the {@link Square}
     * @return Column value [0,7] indicating hte position of the {@link Square} within the {@link Board}.
     */
    public final int getColumn() {
        return this.column;
    }

    /**
     * Determines the color of the square from its position in the {@link Board}.
     * @return {@link Color} enumeration indicating the color of the {@link Square}.
     */
    private Color getColor() {
        Color color;
        if((this.row % 2 == 0 && this.column % 2 == 0) ||
                (this.row % 2 == 1 && this.column % 2 == 1)) {
            color = Color.WHITE;
        } else {
            color = Color.BLACK;
        }
        return color;
    }

    /**
     * Initializes the color of the square.
     */
    private void initializeBackgroundColor() {
        String style = switch (this.getColor()) {
            case BLACK -> defaultBlackStyle;
            case WHITE -> defaultWhiteStyle;
            default -> throw new RuntimeException("Invalid color encountered.");
        };
        this.setStyle(style);
    }

    private void setHighlightedStyle() {
        this.setStyle(highlightedStyle);
    }

    private void onMouseEntered() {
        List<Square> availableSquares;
        if (this.piece != null && (availableSquares = piece.computeAvailableSquares(true)).size() > 0) {
            for (Square square : availableSquares) {
                square.setHighlightedStyle();
            }
        }
    }

    private void onMouseExited() {
        List<Square> availableSquares;
        if (this.piece != null && (availableSquares = piece.computeAvailableSquares(true)).size() > 0) {
            for (Square square : availableSquares) {
                square.initializeBackgroundColor();
            }
        }
    }

    private boolean isEnPassantField(Piece movingPiece) {
        Piece piece;
        return movingPiece instanceof Pawn && ((this.row == 2
                                               && (piece = board.getSquare(this.column, 3).getPiece()) instanceof Pawn
                                               && board.getGame().currentTurn() - piece.getFirstTurnMoved() == 1)
                                              ||
                                              (this.row == 5
                                               && (piece = board.getSquare(this.column, 4).getPiece()) instanceof Pawn
                                               && board.getGame().currentTurn() - piece.getFirstTurnMoved() == 1));
    }

    private void onDragDetected(MouseEvent e) {
        List<Square> squares;
        if (piece != null && piece.canMove() && (squares = piece.getAllAvailableSquares()).size() > 0) {
            Dragboard dragboard = startDragAndDrop(TransferMode.MOVE);
            dragboard.setDragView(piece.getImage());
            dragboard.setDragViewOffsetX(dragOffset);
            dragboard.setDragViewOffsetY(dragOffset);
            ClipboardContent clipboardContent = new ClipboardContent();
            clipboardContent.put(Piece.CHESS_PIECE, piece);
            dragboard.setContent(clipboardContent);
            for (Square square : squares) {
                if (square.getPiece() != null || square.isEnPassantField(piece)) {
                    square.setHighlightedStyle();
                } else {
                    square.initializeBackgroundColor();
                }
            }
            e.consume();
        }
    }

    private void onDragOver(DragEvent e) {
        if (e.getDragboard().hasContent(Piece.CHESS_PIECE)) {
            e.acceptTransferModes(TransferMode.MOVE);
        }
        e.consume();
    }

    private void onDragDone(DragEvent e) {
        Dragboard dragboard = e.getDragboard();
        if (dragboard.hasContent(Piece.CHESS_PIECE)) {
            Piece piece = deserializePiece(dragboard);
            piece.computeAvailableSquares(true).forEach(Square::initializeBackgroundColor);
        }
        e.consume();
    }

    private void onDragDropped(DragEvent e) {
        Dragboard dragboard = e.getDragboard();
        if (dragboard.hasContent(Piece.CHESS_PIECE)) {
            Piece movingPiece = deserializePiece(dragboard);
            movingPiece = board.getSquare(movingPiece.getColumn(), movingPiece.getRow()).getPiece();
            if (movingPiece.canMoveTo(this)) {
                initializeBackgroundColor();
                movingPiece.computeAvailableSquares(true).forEach(Square::initializeBackgroundColor);
                movingPiece.move(this, true);
                ChessGame.GAME_STATE gameState = getBoard().getGame().advanceTurn();
                ChessGame.setLastMoveGameState(gameState);
            }
        }
        e.consume();
    }

    private Piece deserializePiece(Dragboard dragboard) {
        Piece source = (Piece)dragboard.getContent(Piece.CHESS_PIECE);
        source.setSquare(ChessGame.getGameboard().getSquare(source.getColumn(), source.getRow()));
        return source;
    }
}
