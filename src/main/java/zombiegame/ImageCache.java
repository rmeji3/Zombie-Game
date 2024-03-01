package zombiegame;

import javafx.scene.image.Image;
import java.util.HashMap;

public class ImageCache {
    private static final HashMap<String, Image> cache = new HashMap<>();

    public static Image getImage(String path) {
        if (!cache.containsKey(path)) {
            cache.put(path, new Image(Map.class.getResourceAsStream(path)));
        }
        return cache.get(path);
    }
}
