package org.example.zombiegame;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

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

    private final ToolBar toolBar;

    private Rectangle playerCharacter;
    private Set<KeyCode> pressedKeys = new HashSet<>();

    private AnimationTimer gameLoop;

    private StackPane root;
    private GridPane gridPane;

    private Map map;
    PlacableObjectMap placableObjectMap;

    public Player(StackPane root, GridPane gridPane, Map map, PlacableObjectMap placableObjectMap, ToolBar toolBar) {
        this.root = root;
        this.gridPane = gridPane;
        this.map = map;
        this.placableObjectMap = placableObjectMap;
        this.toolBar = toolBar;

        setPosition(0, 0);
        drawPlayer();
        setupGameLoop();
        addToRoot();
    }

    private void setupGameLoop() {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                mouseHandler();
                keyboardHandler();
                handleMovement();
            }
        };
        gameLoop.start();
    }

    private void mouseHandler(){
        highlightTileOnMouse();
        placeObjectOnMouse();
    }

    private void highlightTileOnMouse(){
        root.setOnMouseMoved(event -> {
            double mouseX = event.getSceneX();
            double mouseY = event.getSceneY();

            double gridPaneX = mouseX - gridPane.getLayoutX();
            double gridPaneY = mouseY - gridPane.getLayoutY();

            double worldX = gridPaneX + x;
            double worldY = gridPaneY + y;

//            System.out.println("Mouse x: " + mouseX + " y: " + mouseY);
//            System.out.println("GridPane x: " + gridPaneX + " y: " + gridPaneY);
//            System.out.println("World x: " + worldX + " y: " + worldY);


//            placableObjectMap.placeObjectAtPosition(worldX, worldY, currItemID, false);
        });
    }

    private void placeObjectOnMouse() {
        root.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                double mouseX = event.getSceneX();
                double mouseY = event.getSceneY();

                double gridPaneX = mouseX - gridPane.getLayoutX();
                double gridPaneY = mouseY - gridPane.getLayoutY();

                double worldX = gridPaneX + x;
                double worldY = gridPaneY + y;

//                System.out.println("Mouse x: " + mouseX + " y: " + mouseY);
//                System.out.println("GridPane x: " + gridPaneX + " y: " + gridPaneY);
//                System.out.println("World x: " + worldX + " y: " + worldY);
                int currItemID = toolBar.getCurrentItem();
                System.out.println("current id: " + currItemID);
                System.out.println("current slot: " + toolBar.currInvSlot);
                if(currItemID != -1){
                    placableObjectMap.placeObjectAtPosition(worldX, worldY, currItemID, false);
                }


            }
        });
    }

    private void handleMovement() {
        stopMovement();

        checkDiagonal();

        blockBarrier();
        isAboveTile();

        if (pressedKeys.contains(KeyCode.SHIFT)) useStamina();
        else regenerateStamina();

        if (pressedKeys.contains(KeyCode.W)) moveUp();
        if (pressedKeys.contains(KeyCode.S)) moveDown();
        if (pressedKeys.contains(KeyCode.A)) moveLeft();
        if (pressedKeys.contains(KeyCode.D)) moveRight();

        if (pressedKeys.contains(KeyCode.G)) setPosition(0, 0);

        move();
        updatePosition();
        resetSpeed();
    }


    private void drawPlayer(){
        Image image = ImageCache.getImage("/player/player1.png");
        ImagePattern imagePattern = new ImagePattern(image);
        playerCharacter = new Rectangle(map.getTILE_SIZE(), map.getTILE_SIZE());
        playerCharacter.setFill(imagePattern);
    }

    private void isAboveTile() {
        int tileType = map.getTileAtPosition(x, y);
        if(tileType == 3) speed *= 0.5;
    }

    private void move() {
        gridPane.setTranslateX((gridPane.getTranslateX() - deltaX));
        gridPane.setTranslateY((gridPane.getTranslateY() - deltaY));
    }
    private void blockBarrier(){
        double leftBarrier = -((map.getWidthSize() / 2.0) * map.getTILE_SIZE()) + (playerCharacter.getWidth() / 2);
        double rightBarrier = ((map.getWidthSize() / 2.0) * map.getTILE_SIZE()) - (playerCharacter.getWidth() / 2);
        double topBarrier = -((map.getHeightSize() / 2.0) * map.getTILE_SIZE()) + (playerCharacter.getHeight() / 2);
        double bottomBarrier = ((map.getHeightSize() / 2.0) * map.getTILE_SIZE()) - (playerCharacter.getHeight() / 2);

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
    }


    private void stopMovement(){
        deltaX = 0;
        deltaY = 0;
    }
    private void useStamina() {
        if(stamina > 0){
            speed *= 1.5;
            stamina -= 0.1;
        }
    }

    private void resetSpeed(){
        speed = DEFAULT_SPEED;
    }

    private void regenerateStamina(){
        if(stamina < 100)
            stamina+= .05;
    }

    private void keyboardHandler() {
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