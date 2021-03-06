package com.cse682.chess_cse682;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

public class GameMenu extends BorderPane {

    // Private variables
    private Button newGame;
    private Button undo;
    private Button importGame;
    private Button exportGame;
    private Button resign;
    private ButtonBar menuButtonBar;

    /**
     * Constructor to initialize the Game Menu
     * The Game Menu is static, so we don't have to worry about
     * dynamically adding buttons from an admin page
     * */
    public GameMenu(ChessGame chessGame) {
        this.initButtons();
        this.initBar();
    }

    private void initButtons() {
        this.newGame = new Button("New Game");
        this.undo = new Button("Undo");
        this.importGame = new Button("Import");
        this.exportGame = new Button("Export");
        this.resign = new Button("Resign");
    }

    private void initBar() {
        this.menuButtonBar = new ButtonBar();
        this.menuButtonBar.setButtonMinWidth(100);
        ButtonBar.setButtonData(this.newGame, ButtonBar.ButtonData.APPLY);
        ButtonBar.setButtonData(this.undo, ButtonBar.ButtonData.APPLY);
        ButtonBar.setButtonData(this.importGame, ButtonBar.ButtonData.APPLY);
        ButtonBar.setButtonData(this.exportGame, ButtonBar.ButtonData.APPLY);
        ButtonBar.setButtonData(this.resign, ButtonBar.ButtonData.APPLY);
        this.menuButtonBar.getButtons().addAll(this.newGame, this.undo, this.importGame, this.exportGame, this.resign);
    }

    // TODO - whenever functionality gets added to the game, update the event handlers
    //        to do what they intend according to the requirements specifications
    private void buttonActions() {
        this.newGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("New Game Button Pressed");
                ChessGame.resetGame();
            }
        });

        this.undo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                System.out.println("Undo Button Pressed");
                ChessGame.undoMoveHandler();
            }
        });

        this.importGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("Import Button Pressed");
            }
        });

        this.exportGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                System.out.println("Export Button Pressed");
                ChessGame.exportHandler();
            }
        });

        this.resign.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("Resign Button Pressed");
                ChessGame.resignHandler();
            }
        });
    }

    public ButtonBar getMenuButtons() {
        this.buttonActions();
        return this.menuButtonBar;
    }

}
