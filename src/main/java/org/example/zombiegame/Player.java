package org.example.zombiegame;

import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Player {
    private double x;
    private double y;

    private double stamina = 100;

    private boolean exhausted = false;

    private double defaultSpeed = 2;
    private double speed = defaultSpeed;
    private double diagonalSpeedMultiplier = 0.5;
    private Circle playerCharacter;
    private Set<KeyCode> pressedKeys = new HashSet<>();

    private AnimationTimer gameLoop;

    private StackPane root;
    private GridPane gridPane;

    private Map map;

    public Player(StackPane root, GridPane gridPane, Map map) {
        this.playerCharacter = new Circle(20, Color.WHITE);
        this.root = root;
        this.gridPane = gridPane;

        addToRoot();
        setupGameLoop();
        movePlayer();
        this.map = map;

    }

    private void setupGameLoop() {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                handleKeyInput();

            }
        };
        gameLoop.start();
    }

    private void addToRoot() {
        root.getChildren().add(playerCharacter);
    }

    private void handleKeyInput() {
        x = 0;
        y = 0;

        if (pressedKeys.contains(KeyCode.W)) y -= speed;
        if (pressedKeys.contains(KeyCode.S)) y += speed;
        if (pressedKeys.contains(KeyCode.A)) x -= speed;
        if (pressedKeys.contains(KeyCode.D)) x += speed;
        if(pressedKeys.contains(KeyCode.SHIFT)) {
            if(stamina > 0){
                speed = defaultSpeed * 1.5;
                stamina -= 0.1;
            }else{
                speed = defaultSpeed;
            }



        }
        else{
            if(stamina < 100)
                stamina+= .05;

            speed = defaultSpeed;
        }
        System.out.println(stamina);
        if (x != 0 && y != 0) {
            x *= Math.sqrt(diagonalSpeedMultiplier);
            y *= Math.sqrt(diagonalSpeedMultiplier);
        }

//        gridPane.setTranslateX(gridPane.getTranslateX() - x);
//        gridPane.setTranslateY(gridPane.getTranslateY() - y);

//        double rootX = root.getWidth()/2;
//        double rootY = root.getHeight()/2;

        // move the map
//        isAboveTile(rootX,rootY ,map.lakeList, .5 );
        gridPane.setTranslateX(Math.round(gridPane.getTranslateX() - x));
        gridPane.setTranslateY(Math.round(gridPane.getTranslateY() - y));


        //System.out.println("Player x: " + x + " y: " + y);
    }

    private void movePlayer() {
        root.setOnKeyPressed(event -> {
            pressedKeys.add(event.getCode());
        });

        root.setOnKeyReleased(event -> {
            pressedKeys.remove(event.getCode());
        });

    }

    public void isAboveTile(double playerPosX, double playerPosY, ArrayList<Rectangle> tileArray, double newSpeed) {
        // Assume speed is a property of the player or some context we can modify
        boolean isWithinTile = false;

        // Iterate through each tile to check if the player is within it
        for(Rectangle tile : tileArray){
            // Get player's position and size
//           double playerPosX = playerCharacter.getCenterX();
//           double playerPosY = playerCharacter.getCenterY();

            // Get tile's position and size
            double tileMinX = tile.getX();
            double tileMaxX = tile.getX() + tile.getWidth();
            double tileMinY = tile.getY();
            double tileMaxY = tile.getY() + tile.getHeight();

            System.out.println("Tile x: "  +tileMinX + " Tile y: " + tileMaxY);
            System.out.println("player x: "  +playerPosX + " player y: " + playerPosY);

            // Check if the player is within the tile
            if(playerPosX > tileMinX && playerPosX < tileMaxX && playerPosY > tileMinY && playerPosY < tileMaxY){
                isWithinTile = true;
                break; // Exit the loop as we only need one tile to slow the player
            }
        }

        // Adjust the player's speed based on whether they are within a tile
        if(isWithinTile){
            speed = newSpeed; // Assuming 'speed' is accessible and modifiable here
        } else {
            speed = defaultSpeed; // Assuming 'defaultSpeed' is defined and accessible
        }
    }

}