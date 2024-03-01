package zombiegame.entities;

import zombiegame.Map;
import zombiegame.ToolBar;
import zombiegame.entities.placable.Stone;
import zombiegame.entities.placable.Tree;
import javafx.scene.layout.GridPane;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class PlaceableObjectMap {
    private final int DEFAULT_TILE_SIZE = 50;
    private int tileSize;
    private int widthSize;
    private int heightSize;
    private int[][] grid;
    private String mapFileName;

    private GridPane gridPane;

    private Map map;
    private ArrayList<PlaceableObject> placableObjectList;

    ToolBar toolBar;

    public PlaceableObjectMap(GridPane gridPane, Map map, ToolBar toolBar){
        this.gridPane = gridPane;
        this.map = map;
        this.mapFileName = "objectMap.txt";
        this.toolBar = toolBar;

        tileSize = DEFAULT_TILE_SIZE;
        widthSize = map.getWidthSize();
        heightSize = map.getHeightSize();
        grid = new int[widthSize][heightSize];
        placableObjectList = new ArrayList<>();

        readObjectsMap(mapFileName);
    }

    public void placeObjectAtPosition(double x, double y, int objectID, boolean withMapOffset){
        double mapOffset = 0;

        if(withMapOffset) mapOffset = widthSize * heightSize;


        int gridX = (int)Math.floor((y + mapOffset) / DEFAULT_TILE_SIZE); // row-major
        int gridY = (int)Math.floor((x + mapOffset) / DEFAULT_TILE_SIZE); // row-major

        if (gridX < 0 || gridX >= widthSize || gridY < 0 || gridY >= heightSize) {
            System.out.println("Can't place object here. (Out of bounds)");
            return; // Out of bounds
        }

        if(grid[gridX][gridY])
        placableObjectList.add(getObject(objectID));
        grid[gridX][gridY] = objectID;
        paintObject(gridX, gridY, getObject(objectID));
    }



    private void paintObject(int gridX, int gridY, PlaceableObject placableObject){

//        toolBar.printToolBar();
        if(toolBar.getCurrentItem() != -1 && toolBar.checkItemCount(placableObject)) {
//            System.out.println(placableObject.getItemName() + " count: " + toolBar.getItemCount(placableObject));
            GridPane.setRowIndex(placableObject.getObjectTile(), gridX);
            GridPane.setColumnIndex(placableObject.getObjectTile(), gridY);
            gridPane.getChildren().add(placableObject.getObjectTile());
        }
        else
            System.out.println("no more "  + placableObject.getItemName());
    }

    private PlaceableObject getObject(int objectID){
        PlaceableObject newPlacableObject = null;

        switch(objectID) {
            case 0:
                newPlacableObject = new Tree();
                break;
            case 1:
                newPlacableObject = new Stone();
                break;
            default:
                newPlacableObject = new Tree();
        }

        return newPlacableObject;
    }

    private void readObjectsMap(String fileName){
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader("src/main/resources/objectMaps/" + fileName));
            String currentLine;
            currentLine = reader.readLine();

            String firstNum;
            String secondNum;
            int comma = currentLine.indexOf(',');

            firstNum = currentLine.substring(0, comma);
            secondNum = currentLine.substring(comma+1);

            widthSize = Integer.parseInt(firstNum);
            heightSize = Integer.parseInt(secondNum);

            grid = new int[widthSize][heightSize];

            int row = 0;

            while ((currentLine = reader.readLine()) != null) {
                String[] nums = currentLine.split(" ");
                for(int col = 0; col < nums.length && col < heightSize; col++){
                    grid[row][col] = Integer.parseInt(nums[col]);
                }
                row++;
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (reader != null)
                {
                    reader.close();
                }
            }
            catch
            (IOException ex)
            {
                ex.printStackTrace();
            }
        }
    }
}
