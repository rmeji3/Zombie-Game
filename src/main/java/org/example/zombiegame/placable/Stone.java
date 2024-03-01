package org.example.zombiegame.placable;

import org.example.zombiegame.ImageCache;
import org.example.zombiegame.PlacableObject;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Stone extends PlacableObject {

    static final Image image = ImageCache.getImage("/objects/stones/stone.png");
    static final  ImagePattern imagePattern = new ImagePattern(image);

    static final int objectID = 1;

    static final String name = "Stone";
    public Stone() {
        super();


        objectTile = new Rectangle(TILE_SIZE * objectWidth, TILE_SIZE * objectHeight);
        objectTile.setFill(imagePattern);
    }
    @Override
    public ImagePattern getImagePattern() {
        return imagePattern;
    }
    @Override
    public String getItemName(){
        return name;
    }
    @Override
    public int getObjectID(){
        return objectID;
    }

}
