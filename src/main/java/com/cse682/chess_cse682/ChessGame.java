package com.cse682.chess_cse682;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * ChessClientApplication is a desktop Chess game application with
 * a GUI that allows users to play, load, and save chess games.
 */
public class ChessGame extends Application {

    /**
     * Store a reference to the currently active chess Board.
     */
    private Board gameboard;

    private int turn;

    /**
     * Title of the application window.
     */
    private static final String TITLE = "CSE 682 Chess Application";

    /**
     * Entry point for the ChessClientApplication.  Initializes the stage
     * as well as the data structures that drive the Chess game within the window.
     * @param stage Stage referencing the application window.
     */
    @Override
    public void start(Stage stage) {
        // Set the title into the application window.
        stage.setTitle(TITLE);

        // Initialize the pane used to frame all application contents.
        BorderPane pane = new BorderPane();

        // Set up a frame around the board to hold labels and leaves space
        // for the cells of the chess board.
        GridPane board = new GridPane();
        for(int i = 0; i < 8; i++) {
            // Add the labels for each of the ranks and files of the board to the
            // margins.
            board.add(createRowLabel(i), 0, i+1, 1, 1);
            board.add(createRowLabel(i), 9, i+1, 1, 1);
            board.add(createColumnLabel(i), i+1, 0, 1, 1);
            board.add(createColumnLabel(i), i+1, 9, 1, 1);
        }

        // Instantiate the chess Board instance and place it into the grid using
        // the required offsets to fit within the row/column labels.
        this.setGameboard(new Board(this));
        board.add(this.gameboard, 1, 1, 8, 8);
        board.setAlignment(Pos.CENTER);
        pane.setCenter(board);

        // Game Menu
        GameMenu menu = new GameMenu();
        pane.getChildren().addAll(menu.getMenuButtons());

        initializeGameboard();

        this.setTurn(1);

        // TODO: Add a menu to support additional behaviors like saving/loading/new game, etc.
        BorderPane menu = new BorderPane();

        // Wrap the created Pane into a Scene of a specified size and then display
        // the Scene using the application's Stage.
        Scene scene = new Scene(pane, 470, 520);
        stage.setMinWidth(stage.getWidth());
        stage.setMinHeight(stage.getHeight());
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Get the currently active chess board.
     * @return Current chess board instance shown in the window.
     */
    public Board getGameboard() {
        return this.gameboard;
    }

    /**
     * Set the currently active chess board for the application.
     * @param gameboard Chessboard to display in the Application window.
     */
    public void setGameboard(Board gameboard) {
        if(gameboard == null)
            throw new NullPointerException("Cannot set a null gameboard in the ChessClientApplication.");
        this.gameboard = gameboard;
    }

    /**
     * Set the current turn of the Chess game.
     * @param turn Current turn in the chess game.
     */
    public void setTurn(int turn) {
        this.turn = turn;
    }

    /**
     * Get the current turn of the chess game.
     * @return Current turn of the chess game.
     */
    public int currentTurn() {
        return this.turn;
    }

    /**
     * Utility method to generate a label for a row of the chess board.
     * @param rowNumber Row number [0,7] of the chess board to be labeled.
     * @return Label containing the correct annotation for the row of the chess board.
     */
    private Label createRowLabel(int rowNumber) {
        Label rowLabel = new Label(String.valueOf(8 - rowNumber));
        rowLabel.setAlignment(Pos.CENTER);
        rowLabel.setMinSize(20, 50);
        return rowLabel;
    }

    /**
     * Utility method to generate a label for a column of the chess board.
     * @param columnNumber Column number [0,7] of the chess board to be labeled.
     * @return Label containing the correct annotation for the column of the chess board.
     */
    private Label createColumnLabel(int columnNumber) {
        Label columnLabel = new Label((char)(columnNumber+65) + "");
        columnLabel.setAlignment(Pos.CENTER);
        columnLabel.setMinSize(50, 20);
        return columnLabel;
    }

    /**
     * Utility method to initialize the chess board with the pieces in their default positions.
     *
     * TODO: Once we implement the ability to save and load games, we could just load a file for the default instead.
     */
    private void initializeGameboard() {
        Square square;

        // Place the pawns.
        for(int i = 0; i < 8; i++) {
            square = this.gameboard.getSquare(i, 1);
            square.setPiece(new Pawn(Color.BLACK, square));
            square = this.gameboard.getSquare(i, 6);
            square.setPiece(new Pawn(Color.WHITE, square));
        }

        // Place the rooks.
        square = gameboard.getSquare(0, 0);
        square.setPiece(new Rook(Color.BLACK, square));
        square = gameboard.getSquare(7, 0);
        square.setPiece(new Rook(Color.BLACK, square));
        square = gameboard.getSquare(0, 7);
        square.setPiece(new Rook(Color.WHITE, square));
        square = gameboard.getSquare(7, 7);
        square.setPiece(new Rook(Color.WHITE, square));

        // Place the knights.
        square = gameboard.getSquare(1, 0);
        square.setPiece(new Knight(Color.BLACK, square));
        square = gameboard.getSquare(6, 0);
        square.setPiece(new Knight(Color.BLACK, square));
        square = gameboard.getSquare(1, 7);
        square.setPiece(new Knight(Color.WHITE, square));
        square = gameboard.getSquare(6, 7);
        square.setPiece(new Knight(Color.WHITE, square));

        // Place the bishops.
        square = gameboard.getSquare(2, 0);
        square.setPiece(new Bishop(Color.BLACK, square));
        square = gameboard.getSquare(5, 0);
        square.setPiece(new Bishop(Color.BLACK, square));
        square = gameboard.getSquare(2, 7);
        square.setPiece(new Bishop(Color.WHITE, square));
        square = gameboard.getSquare(5, 7);
        square.setPiece(new Bishop(Color.WHITE, square));

        // Place the queens.
        square = gameboard.getSquare(3, 0);
        square.setPiece(new Queen(Color.BLACK, square));
        square = gameboard.getSquare(3, 7);
        square.setPiece(new Queen(Color.WHITE, square));

        // Place the kings.
        square = gameboard.getSquare(4, 0);
        square.setPiece(new King(Color.BLACK, square));
        square = gameboard.getSquare(4, 7);
        square.setPiece(new King(Color.WHITE, square));
    }
}
