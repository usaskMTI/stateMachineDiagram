module com.example.cmpt381_a3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.cmpt381_a3 to javafx.fxml;
    exports com.example.cmpt381_a3;
}