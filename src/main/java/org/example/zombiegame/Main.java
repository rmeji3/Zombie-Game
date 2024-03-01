package org.example.zombiegame;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.example.zombiegame.placable.Stone;
import org.example.zombiegame.placable.Tree;

public class Main extends Application {
    int sceneWidth = 1280;
    int sceneHeight = 720;

    Map map;
    PlacableObjectMap placableObjectMap;
    Player player;

    Scene scene;
    GridPane gridPane;
    StackPane root;

    ToolBar toolBar;



    @Override
    public void start(Stage stage)  {
        // Panes
        root = new StackPane();
        gridPane = new GridPane();

        gridPane.setAlignment(Pos.CENTER);
        root.getChildren().add(gridPane);

        // Objects
        map = new Map(gridPane);
        toolBar = new ToolBar(root);
        toolBar.addToInventory(new Tree(), 10, 0);
        toolBar.addToInventory(new Stone(), 10, 1);
        placableObjectMap = new PlacableObjectMap(gridPane, map, toolBar);
        player = new Player(root, gridPane, map, placableObjectMap, toolBar);

//        placableObjectMap.placeObjectAtPosition(55, 0, 0, true, 1);
//        placableObjectMap.placeObjectAtPosition(55, 55, 1, true, 2);


        // Scene
        scene = new Scene(root, sceneWidth, sceneHeight);


        stage.setScene(scene);
        scene.setOnKeyPressed(event -> {
            toolBar.buttonHandler(event);});
        root.setFocusTraversable(true);
        root.setStyle(
                "-fx-background-color: #247abf;"
        );


        root.requestFocus();
        stage.setTitle("Zombie.io");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}