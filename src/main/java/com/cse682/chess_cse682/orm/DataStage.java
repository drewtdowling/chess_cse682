package com.cse682.chess_cse682.orm;

import com.cse682.chess_cse682.login.LoginController;
import javafx.stage.Stage;

public class DataStage extends Stage {
    public User showAndReturn(LoginController loginController) {
        super.showAndWait();
        return loginController.getLoggedInUser();
    }
}
