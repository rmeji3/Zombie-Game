package zombiegame.entities.placable;

import zombiegame.ImageCache;
import zombiegame.entities.PlaceableObject;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Tree extends PlaceableObject {

    public Tree() {
        super();

        name = "Tree";
        image = ImageCache.getImage("/objects/trees/tree.png");
        imagePattern = new ImagePattern(image);
        objectID = 0;
        objectTile = new Rectangle(TILE_SIZE * objectWidth, TILE_SIZE * objectHeight);
        objectTile.setFill(imagePattern);
    }
}
