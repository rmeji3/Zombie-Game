package org.example.zombiegame;

import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.HashSet;
import java.util.Set;

public class Player {
    private double x;
    private double y;
    private double speed = 2;
    private double diagonalSpeedMultiplier = 0.5;
    private Circle playerCharacter;
    private Set<KeyCode> pressedKeys = new HashSet<>();
    private AnimationTimer gameLoop;

    private StackPane root;
    private GridPane gridPane;

    public Player(StackPane root, GridPane gridPane) {
        this.playerCharacter = new Circle(20, Color.WHITE);
        this.root = root;
        this.gridPane = gridPane;

        addToRoot();
        setupGameLoop();
        movePlayer();
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

        if (x != 0 && y != 0) {
            x *= Math.sqrt(diagonalSpeedMultiplier);
            y *= Math.sqrt(diagonalSpeedMultiplier);
        }

//        gridPane.setTranslateX(gridPane.getTranslateX() - x);
//        gridPane.setTranslateY(gridPane.getTranslateY() - y);

        // move the map
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
}