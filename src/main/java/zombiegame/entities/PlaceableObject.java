package zombiegame.entities;

import javafx.scene.shape.Rectangle;

public class PlaceableObject extends Entity{
    protected Rectangle objectTile;
    protected int objectWidth;
    protected int objectHeight;
    public PlaceableObject(){
        objectWidth = 1;
        objectHeight = 1;
    }

    public Rectangle getObjectTile(){
        return objectTile;
    }
}
