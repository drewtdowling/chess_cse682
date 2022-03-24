package com.cse682.chess_cse682;

import com.cse682.chess_cse682.db.DataAccessException;
import com.cse682.chess_cse682.db.GenerateDB;
import com.cse682.chess_cse682.orm.DataStage;
import com.cse682.chess_cse682.orm.User;
import com.cse682.chess_cse682.piece.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Objects;
import java.io.Serializable;

/**
 * ChessClientApplication is a desktop Chess game application with
 * a GUI that allows users to play, load, and save chess games.
 */
public class ChessGame extends Application implements Serializable {

    /**
     * Store a reference to the currently active chess Board.
     */
    private static Board gameboard;

    /**
     * Game status label to show within the window.
     */
    private static transient Label gameStatus;

    /**
     * Current turn in the chess game.
     */
    private static int turn;

    /**
     * Singleton instance used to track the current game.
     */
    private static ChessGame game;


    private static ArrayList<Move> moveHistory;


    /**
     * Title of the application window.
     */
    private static final String TITLE = "Simple Chess";

    /**
     * Entry point for the ChessClientApplication.  Initializes the stage
     * as well as the data structures that drive the Chess game within the window.
     * @param stage Stage referencing the application window.
     */
    @Override
    public void start(Stage stage) throws Exception {
        // Init DB
        try {
            this.initDB();
        }
        catch (DataAccessException e) {
            System.err.println("Something went wrong initializing the database");
        }

        // Show the login stage
        // Login page for White player
        DataStage whitePlayerLogin = new DataStage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/loginForm.fxml"));
        Parent root = loader.load();
        Scene whiteLoginScene = new Scene(root);
        whitePlayerLogin.setScene(whiteLoginScene);
        User whitePlayer = whitePlayerLogin.showAndReturn(loader.getController());

        // Login page for Black player
        DataStage blackPlayerLogin = new DataStage();
        FXMLLoader blackLoader = new FXMLLoader(getClass().getResource("/fxml/loginFormBlack.fxml"));
        Parent blackRoot = blackLoader.load();
        Scene blackLoginScene = new Scene(blackRoot);
        blackPlayerLogin.setScene(blackLoginScene);
        User blackPlayer = blackPlayerLogin.showAndReturn(blackLoader.getController());

        // Set the title into the application window.
        stage.setTitle(TITLE);

        // Set the singleton.
        game = this;

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

        // Initialize Game History
        moveHistory = new ArrayList<>();

        // Instantiate the chess Board instance and place it into the grid using
        // the required offsets to fit within the row/column labels.
        this.setGameboard(new Board(this));
        board.add(gameboard, 1, 1, 8, 8);
        board.setAlignment(Pos.CENTER);
        pane.setCenter(board);

        // Game Menu
        GameMenu menu = new GameMenu(this);
        ButtonBar buttonBar = menu.getMenuButtons();
        HBox menuBox = new HBox();
        menuBox.setAlignment(Pos.CENTER);
        menuBox.setPadding(new Insets(10, 20, 10, 0));
        menuBox.getChildren().addAll(buttonBar);
        menuBox.setStyle("-fx-background-color: darkgreen");
        pane.setTop(menuBox);

        // Game Status
        HBox statusBox = new HBox();
        statusBox.setAlignment(Pos.CENTER);
        statusBox.setStyle("-fx-background-color: darkgreen");
        gameStatus = new Label();
        gameStatus.setAlignment(Pos.CENTER);
        gameStatus.setPadding(new Insets(10, 10, 10, 10));
        gameStatus.setStyle("-fx-background-color: darkgreen; -fx-text-fill: white");
        statusBox.getChildren().add(gameStatus);
        pane.setBottom(statusBox);

        initializeGameboard();

        this.setTurn(1);

        game.checkGameState();

        // Wrap the created Pane into a Scene of a specified size and then display
        // the Scene using the application's Stage.

        Scene scene = new Scene(pane, 600, 550);
        stage.setMinWidth(stage.getWidth());
        stage.setMinHeight(stage.getHeight());
        stage.setScene(scene);
        stage.show();

    }

    /**
     * Get the currently active chess board.
     * @return Current chess board instance shown in the window.
     */
    public static Board getGameboard() {
        return gameboard;
    }

    /**
     * Set the currently active chess board for the application.
     * @param gameboard Chessboard to display in the Application window.
     */
    public void setGameboard(Board gameboard) {
        if(gameboard == null)
            throw new NullPointerException("Cannot set a null gameboard in the ChessClientApplication.");
        ChessGame.gameboard = gameboard;
    }

    /**
     * Set the current turn of the Chess game.
     * @param turn Current turn in the chess game.
     */
    public void setTurn(int turn) {
        this.turn = turn;
    }

    /**
     * Utility method to advance the turn of the game after a move.
     */
    public GAME_STATE advanceTurn() {

        turn++;
        gameboard.recalculateAttackedSquares();
        return checkGameState();
    }

    /**
     * Utility method to update the text in the game status label shown in the GUI.
     */
    private static void displayGameState(String gameStateText) {
        gameStatus.setText(gameStateText);
    }

    /**
     * Enum use for game state
     */
    public enum GAME_STATE {
        NORMAL, CHECK, CHECKMATE, STALEMATE
    }

    /**
     * Check the state of the chess game for checks, checkmates, and stalemates.
     */
    public GAME_STATE checkGameState() {
        King king = gameboard.getKing(nextToMove());
        if (king != null) {
            if (king.inCheck()) {
                if (king.inCheckmate()) {
                    displayGameState("Checkmate!  " + king.getColor().opposite().prettyName() + " wins!");
                    return GAME_STATE.CHECKMATE;
                } else {
                    displayGameState("Check! " + king.getColor().prettyName() + " to defend.");
                    return GAME_STATE.CHECK;
                }
            } else if (king.inStalemate()) {
                displayGameState("Stalemate! " + king.getColor().prettyName() + " can't move.");
                return GAME_STATE.STALEMATE;
            }
        }
        displayGameState(nextToMove().prettyName() + " to move!");
        return GAME_STATE.NORMAL;
    }

    /**
     * Get the current turn of the chess game.
     * @return Current turn of the chess game.
     */
    public int currentTurn() {
        return turn;
    }

    /**
     * Utility method to determine the color of the player next to move.
     * @return Color
     */
    public Color nextToMove() {
        return turn % 2 == 0 ? Color.BLACK : Color.WHITE;
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
    private static void initializeGameboard() {
        Square square;

        // Place the pawns.
        for(int i = 0; i < 8; i++) {
            square = gameboard.getSquare(i, 1);
            square.setPiece(new Pawn(Color.BLACK, square));
            square = gameboard.getSquare(i, 2);
            square.setPiece(null);
            square = gameboard.getSquare(i, 3);
            square.setPiece(null);
            square = gameboard.getSquare(i, 4);
            square.setPiece(null);
            square = gameboard.getSquare(i, 5);
            square.setPiece(null);
            square = gameboard.getSquare(i, 6);
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

        game.checkGameState();
    }

    /**
     * Utility method to reset the game board, reset the turn, and reset
     * the game status label.
     */
    public static void resetGame() {
        initializeGameboard();
        turn = 1;
        game.checkGameState();
        moveHistory = new ArrayList<>();
    }

    /**
     * Handler that will be called by the undo button in GameMenu
     */
    public static void undoMoveHandler() {
        if(turn!=1){
            // Set Piece to previous position
            Move previousMove = moveHistory.get(moveHistory.size()-1);
            Square square = gameboard.getSquare(previousMove.previousCol,previousMove.previousRow);
            Piece previousPiece = previousMove.piece;
            previousPiece.setSquare(square);
            square.setPiece(previousMove.piece);
            square = gameboard.getSquare(previousMove.currentCol, previousMove.currentRow);
            square.setPiece(previousMove.killedPiece);
            moveHistory.remove(previousMove);

            turn--;
            gameboard.recalculateAttackedSquares();
            game.checkGameState();
        }
    }

    /**
     * Add a Move to the moveHistory Array
     */
    public static void addMoveHistory(Move move){
        moveHistory.add(move);
    }

    public static void setLastMoveGameState(GAME_STATE gameState){
        moveHistory.get(moveHistory.size()-1).gameState = gameState;
    }
    /**
     * Move class that stores the information of the move.
     */
    public static class Move {
        public Piece piece;
        public Piece killedPiece;   //Could be null when no piece is killed
        public int previousRow;
        public int previousCol;
        public int currentRow;
        public int currentCol;
        public GAME_STATE gameState;
        public Move(Piece piece, int previousRow, int previousCol, int currentRow,
                    int currentCol, Piece killedPiece){
            this.piece = piece;
            this.previousRow = previousRow;
            this.previousCol = previousCol;
            this.currentRow = currentRow;
            this.currentCol = currentCol;
            this.killedPiece = killedPiece;
            this.gameState = GAME_STATE.NORMAL;
        }
    }

    /**
     * Handler that will be called by the resign button in GameMenu
     */
    public static void resignHandler(){
        if(turn % 2 == 0){
            // Black Resign
            displayGameState("Black Resigned, White Won!");
        } else {
            // White Resign
            displayGameState("White Resigned, Black Won!");
        }
    }

    public static void importHandler(){

    }

    public static void exportHandler(){

        StringBuilder exportString = new StringBuilder();
        for(int i = 0; i < moveHistory.size(); i++){
            if(i%2==0){
                exportString.append(String.format("%d. ", (int) Math.floor(i / 2) + 1));
            }
            Move move = moveHistory.get(i);
            switch (move.piece.getName()) {
                case "king" -> exportString.append("K");
                case "queen" -> exportString.append("Q");
                case "knight" -> exportString.append("N");
                case "bishop" -> exportString.append("B");
                case "rook" -> exportString.append("R");
            }
            if(move.killedPiece != null){
                exportString.append((char) (move.previousCol + 97));
                exportString.append("x");
            }
            exportString.append((char) (move.currentCol + 97));
            exportString.append(Integer.toString(8 - move.currentRow));
            switch (move.gameState) {
                case CHECK -> exportString.append("+");
                case CHECKMATE -> {
                    exportString.append("#");
                    if (move.piece.getColor() == Color.WHITE) {
                        exportString.append(" 1-0");
                    } else {
                        exportString.append(" 0-1");
                    }
                }
                case STALEMATE -> exportString.append(" 1/2-1/2");
            }
            exportString.append(" ");
        }
        System.out.println(exportString);
        ExportDialog exportDialog = new ExportDialog(exportString.toString());
        exportDialog.showAndWait();

    }

    /**
     * This function init the database
     * */
    private void initDB() throws DataAccessException {
        GenerateDB gdb = new GenerateDB();
        gdb.generateUserTable();
        gdb.generateStatsTable();
    }

    /**
     * DEBUG - this function clears the tables
     * */
    private void clearDB() throws DataAccessException {
        GenerateDB gdb = new GenerateDB();
        gdb.clearUserTable();
        gdb.clearStatsTable();
    }

    /**
     * This function displays the black and white user in the lower right hand
     * portion of the screen
     * */
    private Label labelUsers(User whiteUser, User blackUser) {
        Label users = new Label();
        users.setPadding(new Insets(10, 0, 10, 10));
        users.setText("White: " + whiteUser.getUsername() + "\n\r" + "Black: " + blackUser.getUsername());
        return users;
    }
}
