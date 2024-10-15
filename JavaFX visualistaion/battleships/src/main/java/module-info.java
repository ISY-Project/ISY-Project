module com.battleships {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.battleships to javafx.fxml;
    exports com.battleships;
}
