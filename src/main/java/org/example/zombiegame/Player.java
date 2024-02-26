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
    private double deltaX;
    private double deltaY;
    private final double DEFAULT_SPEED = 2;
    private final double DIAGONAL_SPEED_MULTIPLIER = Math.sqrt(0.5);
    private double speed = DEFAULT_SPEED;
    private double stamina = 100;

    private Circle playerCharacter;
    private Set<KeyCode> pressedKeys = new HashSet<>();

    private AnimationTimer gameLoop;

    private boolean disableMovementL = false;
    private boolean disableMovementR = false;
    private boolean disableMovementT = false;
    private boolean disableMovementB = false;

    private StackPane root;
    private GridPane gridPane;

    private Map map;

    public Player(StackPane root, GridPane gridPane, Map map) {
        this.playerCharacter = new Circle(20, Color.WHITE);
        this.root = root;
        this.gridPane = gridPane;
        this.map = map;

        setPosition(0, 0);
        useDiagonalSpeedMultiplier();
        handleInput();
        setupGameLoop();
        addToRoot();
    }

    private void setupGameLoop() {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                handleMovement();
            }
        };
        gameLoop.start();
    }

    private void handleMovement() {
        stopMovement();

        //checkDiagonal();
        blockBarrier();
        isAboveTile();

        if (pressedKeys.contains(KeyCode.W)) moveUp();
        if (pressedKeys.contains(KeyCode.S)) moveDown();
        if (pressedKeys.contains(KeyCode.A)) moveLeft();
        if (pressedKeys.contains(KeyCode.D)) moveRight();

        if (pressedKeys.contains(KeyCode.SHIFT)) useStamina();
        else regenerateStamina();

        updatePosition();
        move();

        if (pressedKeys.contains(KeyCode.G)) setPosition(0, 0);

        System.out.println("Position x: " + x + " y: " + y);
    }


    public void isAboveTile() {
        int tileType = map.getTileAtPosition(x, y);
        speed = (tileType == 3) ? DEFAULT_SPEED * 0.5 : DEFAULT_SPEED;
    }

    private void move() {
        gridPane.setTranslateX(Math.round(gridPane.getTranslateX() - deltaX));
        gridPane.setTranslateY(Math.round(gridPane.getTranslateY() - deltaY));
    }
    private void blockBarrier(){
        double leftBarrier = -((map.getWidthSize() / 2.0) * map.getTILE_SIZE()) + playerCharacter.getRadius();
        double rightBarrier = ((map.getWidthSize() / 2.0) * map.getTILE_SIZE()) - playerCharacter.getRadius();
        double topBarrier = -((map.getHeightSize() / 2.0) * map.getTILE_SIZE()) + playerCharacter.getRadius();
        double bottomBarrier = ((map.getHeightSize() / 2.0) * map.getTILE_SIZE()) - playerCharacter.getRadius();

        System.out.println(rightBarrier + " " + x);

        if(x <= leftBarrier){
            setPosition(leftBarrier ,y);
        }
        if(x >= rightBarrier ){
           setPosition(rightBarrier ,y);
        }
        if(y >= bottomBarrier){
            setPosition(x ,bottomBarrier);
        }
        if(y <= topBarrier){
            setPosition(x ,topBarrier);
        }
    }

    private void checkDiagonal(){
        if (    (pressedKeys.contains(KeyCode.W) && pressedKeys.contains(KeyCode.A) ||
                (pressedKeys.contains(KeyCode.W) && pressedKeys.contains(KeyCode.D))) ||
                (pressedKeys.contains(KeyCode.S) && pressedKeys.contains(KeyCode.A)) ||
                (pressedKeys.contains(KeyCode.S) && pressedKeys.contains(KeyCode.D)))
            speed /= 2;
        else speed = DEFAULT_SPEED;

    }

    private void updatePosition(){
        x += deltaX;
        y += deltaY;
    }

    private void moveRight(){
        deltaX += speed;
    }

    private void moveLeft(){
        deltaX -= speed;
    }

    private void moveUp(){
        deltaY -= speed;
    }

    private void moveDown(){
        deltaY += speed;
    }

    private void useDiagonalSpeedMultiplier() {
        deltaX *= DIAGONAL_SPEED_MULTIPLIER;
        deltaY *= DIAGONAL_SPEED_MULTIPLIER;
    }

    private void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
        gridPane.setTranslateX(-x);
        gridPane.setTranslateY(-y);

        isAboveTile();
    }


    private void stopMovement(){
        deltaX = 0;
        deltaY = 0;
    }
    private void useStamina() {
        if(stamina > 0){
            speed = DEFAULT_SPEED * 1.5;
            stamina -= 0.1;
        }
        else{
            speed = DEFAULT_SPEED;
        }
    }

    private void regenerateStamina(){
        if(stamina < 100)
            stamina+= .05;

        speed = DEFAULT_SPEED;
    }

    private void handleInput() {
        root.setOnKeyPressed(event -> {
            pressedKeys.add(event.getCode());
        });

        root.setOnKeyReleased(event -> {
            pressedKeys.remove(event.getCode());
        });
    }

    private void addToRoot() {
        root.getChildren().add(playerCharacter);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}