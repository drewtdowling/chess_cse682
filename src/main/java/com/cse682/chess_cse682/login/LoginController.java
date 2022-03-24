package com.cse682.chess_cse682.login;

import com.cse682.chess_cse682.db.DataAccessException;
import com.cse682.chess_cse682.db.JDBCDao;
import com.cse682.chess_cse682.orm.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Window;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;


public class LoginController extends Controller implements Initializable {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button submitButton;
    @FXML
    private Button registerButton;

    // User
    private User loggedInUser = new User();

    @FXML
    public void login(ActionEvent event) throws DataAccessException {
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
        boolean flag = jDao.validate(uname, pwd);

        if (!flag) {
            infoBox("Please Enter correct email and password", null, "Failed");
        }
        else {
            infoBox("Login Successful", null, "Success");
            Stage stage = (Stage)submitButton.getScene().getWindow();
            stage.close();
        }

        loggedInUser = new User(uname, pwd);
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

    public User getLoggedInUser() {
        return loggedInUser;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
