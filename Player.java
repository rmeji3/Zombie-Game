package org.example.zombiegame;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.HashSet;
import java.util.Set;

public class Player {
    private int x;
    private int y;
    private int speed = 5;

    private Circle playerCharacter;
    private Map gridMap;

    private Set<KeyCode> pressedKeys = new HashSet<>();

    public Player(Map gridMap) {
        this.gridMap = gridMap;
        setCenter();

        playerCharacter = new Circle(20, Color.WHITE); // Set the color here if constant
    }

    // Call this method after adding the player to the root to set its initial position
    public void addToRoot(StackPane root) {
        root.getChildren().add(playerCharacter);
        updatePlayerGraphic();
    }

    private void handleKeyInput() {

        if (pressedKeys.contains(KeyCode.W)) y -= speed; // Move up
        if (pressedKeys.contains(KeyCode.S)) y += speed; // Move down
        if (pressedKeys.contains(KeyCode.A)) x -= speed; // Move left
        if (pressedKeys.contains(KeyCode.D)) x += speed; // Move right
        updatePlayerGraphic(); // Update the player's position graphically
    }

    public void movePlayer(StackPane root) {
        root.setOnKeyPressed(event -> {
            pressedKeys.add(event.getCode());
            handleKeyInput();
        });

        root.setOnKeyReleased(event -> {
            pressedKeys.remove(event.getCode());
        });
    }

    private void updatePlayerGraphic() {
        playerCharacter.setTranslateX(x);
        playerCharacter.setTranslateY(y);
    }

    private void setCenter() {
        x = gridMap.getWidthSize() / 2;
        y = gridMap.getHeightSize() / 2;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
}
