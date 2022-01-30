package com.cse682.chess_cse682;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ChessClientApplication extends Application {

    private static ChessClientApplication instance;
    private Board gameboard;

    @Override
    public void start(Stage stage) {
        instance = this;

        stage.setTitle("CSE 682 Chess Application");

        BorderPane pane = new BorderPane();

        GridPane board = new GridPane();
        for(int i = 0; i < 8; i++) {
            board.add(createRowLabel(i), 0, i+1, 1, 1);
            board.add(createRowLabel(i), 9, i+1, 1, 1);
            board.add(createColumnLabel(i), i+1, 0, 1, 1);
            board.add(createColumnLabel(i), i+1, 9, 1, 1);
        }

        board.add(gameboard = new Board(), 1, 1, 8, 8);
        board.setAlignment(Pos.CENTER);
        pane.setCenter(board);

        BorderPane menu = new BorderPane();
        menu.setPadding(new Insets(10, 10, 10, 0));

        Scene scene = new Scene(pane, 440, 490);
        stage.setScene(scene);
        stage.show();
        stage.setMinWidth(stage.getWidth());
        stage.setMinHeight(stage.getHeight());
    }

    private Label createRowLabel(int rowNumber) {
        Label rowLabel = new Label(String.valueOf(8 - rowNumber));
        rowLabel.setAlignment(Pos.CENTER);
        rowLabel.setMinSize(20, 50);
        return rowLabel;
    }

    private Label createColumnLabel(int columnNumber) {
        Label columnLabel = new Label((char)(columnNumber+65) + "");
        columnLabel.setAlignment(Pos.CENTER);
        columnLabel.setMinSize(50, 20);
        return columnLabel;
    }

}
