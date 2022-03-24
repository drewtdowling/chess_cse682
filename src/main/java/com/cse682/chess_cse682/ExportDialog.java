package com.cse682.chess_cse682;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.GridPane;

public class ExportDialog extends Dialog {
    private final String exportString;
    public ExportDialog(String exportString) {
        this.exportString = exportString;
        setTitle("Export PNG");
        setHeaderText("Copy and paste PNG below:");

        TextArea textArea = new TextArea(exportString);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        Button copyButton = new Button("Copy");
        copyButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                final Clipboard clipboard = Clipboard.getSystemClipboard();
                final ClipboardContent content = new ClipboardContent();
                content.putString(exportString);
                clipboard.setContent(content);
            }
        });
        GridPane gridPane = new GridPane();
        gridPane.add(textArea,0,0);
        gridPane.add(copyButton, 0,1);

        ButtonType buttonType = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().add(buttonType);
        getDialogPane().setContent(gridPane);

    }
}
