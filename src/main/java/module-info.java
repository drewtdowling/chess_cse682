module com.cse682.chess_cse682 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.cse682.chess_cse682 to javafx.fxml;
    exports com.cse682.chess_cse682;
}