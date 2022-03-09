package com.cse682.chess_cse682.login;

import com.cse682.chess_cse682.db.DataAccessException;
import com.cse682.chess_cse682.db.JDBCDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Window;
import javafx.stage.Stage;

import java.sql.SQLException;


public class LoginController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button submitButton;
    @FXML
    private Button registerButton;

    @FXML
    public void login(ActionEvent event) throws DataAccessException {
        Window owner = submitButton.getScene().getWindow();

        System.out.println(usernameField.getText());
        System.out.println(passwordField.getText());

        if (usernameField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form error", "Please enter username");
            return;
        }
        if (passwordField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form error", "Please enter a password");
        }

        String uname = usernameField.getText();
        String pwd = passwordField.getText();

        // Put DB operations here
        JDBCDao jDao = new JDBCDao();
        boolean flag = jDao.validate(uname, pwd);

        if (!flag) {
            infoBox("Please Enter correct email and password", null, "Failed");
        }
        else {
            infoBox("Login Successful", null, "Success");
            Stage stage = (Stage)submitButton.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    public void gotoRegister(ActionEvent event) throws DataAccessException {
        // Close the stage
        Stage stage = (Stage)submitButton.getScene().getWindow();
        stage.close();

        // Show the register page TODO
    }

    private static void infoBox(String infoMsg, String headerText, String title) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(infoMsg);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }

    private static void showAlert(Alert.AlertType alertType, Window owner, String title, String msg) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.initOwner(owner);
        alert.show();
    }


}
