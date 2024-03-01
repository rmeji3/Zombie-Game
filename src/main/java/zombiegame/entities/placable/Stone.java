package zombiegame.entities.placable;

import zombiegame.ImageCache;
import zombiegame.entities.PlaceableObject;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Stone extends PlaceableObject {
    public Stone() {
        super();

        name = "Stone";
        image = ImageCache.getImage("/objects/stones/stone.png");
        imagePattern = new ImagePattern(image);
        objectID = 1;
        objectTile = new Rectangle(TILE_SIZE * objectWidth, TILE_SIZE * objectHeight);
        objectTile.setFill(imagePattern);
    }
}
