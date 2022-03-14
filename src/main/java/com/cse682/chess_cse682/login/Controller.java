package com.cse682.chess_cse682.login;

import javafx.scene.control.Alert;
import javafx.stage.Window;

public class Controller {
    protected static void infoBox(String infoMsg, String headerText, String title) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(infoMsg);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }

    protected static void showAlert(Alert.AlertType alertType, Window owner, String title, String msg) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.initOwner(owner);
        alert.show();
    }
}
