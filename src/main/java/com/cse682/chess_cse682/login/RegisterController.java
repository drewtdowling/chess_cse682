package com.cse682.chess_cse682.login;

import com.cse682.chess_cse682.db.DataAccessException;
import com.cse682.chess_cse682.db.JDBCDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

public class RegisterController extends Controller {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button submitButton;

    @FXML
    public void register(ActionEvent event) throws DataAccessException {
        Window owner = submitButton.getScene().getWindow();

        if (usernameField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form error", "Please enter username");
            return;
        }
        if (passwordField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form error", "Please enter a password");
            return;
        }

        String uname = usernameField.getText();
        String pwd = passwordField.getText();

        // Put DB operations here
        JDBCDao jDao = new JDBCDao();

        jDao.register(uname, pwd);
        infoBox("Registration Successful", null, "Success");

        // Close the stage after registration
        Stage stage = (Stage)submitButton.getScene().getWindow();
        stage.close();
    }

}
