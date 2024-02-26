package org.example.zombiegame;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    int sceneWidth = 1280;
    int sceneHeight = 720;

    Map map;
    Player player;

    Scene scene;
    GridPane gridPane;
    StackPane root;

    @Override
    public void start(Stage stage) throws IOException {
        // Panes
        root = new StackPane();
        gridPane = new GridPane();

        gridPane.setAlignment(Pos.CENTER);
        root.getChildren().add(gridPane);

        // Objects
        map = new Map(gridPane);
        player = new Player(root, gridPane, map);

        // Scene
        scene = new Scene(root, sceneWidth, sceneHeight);
        stage.setScene(scene);

        root.setFocusTraversable(true);
        root.setStyle(
                "-fx-background-color: #247abf;"
        );
        root.requestFocus();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}