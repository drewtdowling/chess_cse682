package com.cse682.chess_cse682.login;

import com.cse682.chess_cse682.db.DataAccessException;
import com.cse682.chess_cse682.db.JDBCDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Window;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;


public class LoginController extends Controller {

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

//        System.out.println(usernameField.getText());
//        System.out.println(passwordField.getText());

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
    public void gotoRegister(ActionEvent event) throws IOException {

        // Show the register page
        Stage regStage = new Stage();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/registerForm.fxml")));
        Scene reg = new Scene(root);
        regStage.setScene(reg);
        regStage.show();
    }




}
