module zombiegame {
    requires javafx.controls;
    requires javafx.fxml;


    opens zombiegame to javafx.fxml;
    exports zombiegame;
    exports zombiegame.entities;
    opens zombiegame.entities to javafx.fxml;
}