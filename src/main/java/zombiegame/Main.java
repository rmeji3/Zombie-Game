package zombiegame;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import zombiegame.entities.PlaceableObjectMap;
import zombiegame.entities.placable.Stone;
import zombiegame.entities.placable.Tree;

public class Main extends Application {
    int sceneWidth = 1280;
    int sceneHeight = 720;

    Map map;
    PlaceableObjectMap placableObjectMap;
    ToolBar toolBar;
    Player player;

    Scene scene;
    GridPane gridPane;
    StackPane root;


    @Override
    public void start(Stage stage)  {
        // Panes
        root = new StackPane();
        gridPane = new GridPane();

        gridPane.setAlignment(Pos.CENTER);
        root.getChildren().add(gridPane);
        toolBar = new ToolBar(root);
        toolBar.addToToolBar(new Tree(), 10);
        toolBar.addToToolBar(new Stone(), 10);
        // Classes
        map = new Map(gridPane);
        placableObjectMap = new PlaceableObjectMap(gridPane, map, toolBar);

//        toolBar.addToToolBar(new Stone(), 10);
        player = new Player(root, gridPane, map, placableObjectMap, toolBar);

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