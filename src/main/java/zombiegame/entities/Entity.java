package zombiegame.entities;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Entity {
    protected final int TILE_SIZE = 50;
    protected int objectID;
    protected String name = "Entity";
    protected Image image;
    protected ImagePattern imagePattern;

    public int getObjectID(){
        return objectID;
    }

    public ImagePattern getImagePattern() {
        return imagePattern;
    }

    public String getItemName(){
        return name;
    }
}
