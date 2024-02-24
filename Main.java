package org.example.zombiegame;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    int sceneWidth = 1000;
    int sceneHeight = 1000;

    Map mapGrid;
    Player player;

    Scene scene;
    GridPane gridPane;
    StackPane root;

    @Override
    public void start(Stage stage) throws IOException {
        // Panes
        root = new StackPane();
        root.setFocusTraversable(true);


        gridPane = new GridPane();

        gridPane.setAlignment(Pos.CENTER);
        root.getChildren().add(gridPane);

        // Objects
        mapGrid = new Map();
        player = new Player(mapGrid);
        player.addToRoot(root);

        // Paint
        mapGrid.paintMap(gridPane);
        player.movePlayer(root);

        // Scene
        scene = new Scene(root, sceneWidth, sceneHeight);
        stage.setScene(scene);

        root.requestFocus();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}