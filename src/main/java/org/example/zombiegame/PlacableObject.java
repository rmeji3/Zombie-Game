package org.example.zombiegame;

import javafx.scene.paint.ImagePattern;
import org.example.zombiegame.Map;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

public class PlacableObject {
    protected final int TILE_SIZE = 50;
    protected int objectID= -1;
    protected Image image;
    protected ImageView imageView;
    protected Rectangle objectTile;

    protected String name = "Placable Object";

    protected int objectWidth = 1;
    protected int objectHeight = 1;


    public PlacableObject(){

    }

    public Rectangle getObjectTile(){
        return objectTile;
    }
    public int getObjectID(){
        return objectID;
    }
    public ImagePattern getImagePattern() {
        // Placeholder implementation
        return null;
    }
    public String getItemName(){
        return name;
    }

}
