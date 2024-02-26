module org.example.zombiegame {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.zombiegame to javafx.fxml;
    exports org.example.zombiegame;
}