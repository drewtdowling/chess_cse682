module com.cse682.chess_cse682 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.cse682.chess_cse682 to javafx.fxml;
    exports com.cse682.chess_cse682;
    exports com.cse682.chess_cse682.piece;
    opens com.cse682.chess_cse682.piece to javafx.fxml;
    opens com.cse682.chess_cse682.login to javafx.fxml;
}