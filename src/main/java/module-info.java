module BarcaGame {
    requires javafx.controls;
    requires javafx.fxml;

    opens  view to javafx.fxml;
    exports view;
    exports model;
    exports model.factories;
}