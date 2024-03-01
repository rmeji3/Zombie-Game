package org.example.zombiegame;

import org.example.zombiegame.placable.Stone;
import org.example.zombiegame.placable.Tree;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Map {

    private final int TILE_SIZE = 50;
    private int widthSize;
    private int heightSize;
    private int[][] grid;
    private String mapFileName;

    private GridPane gridPane;

    public Map(GridPane gridPane){
        this.gridPane = gridPane;
        this.mapFileName = "map.txt";

        readMap(mapFileName);
        paintMap();
    }

    private ImagePattern getTileResource(int tileID){
        String fileName = "/tiles/";

        switch(tileID) {
            case 0:
                fileName += "grasses/grass.png";
                break;
            case 1:
                fileName += "stones/stone.png";
                break;
            case 2:
                fileName += "dirts/dirt.png";
                break;
            case 3:
                fileName += "waters/water.png";
                break;
            case 4:
                fileName += "sands/sand.png";
                break;
            default:
                fileName += "grasses/grass.png";
        }

        Image image = ImageCache.getImage(fileName);
        ImagePattern imagePattern = new ImagePattern(image);

        return imagePattern;
    }
    private void paintMap() {
        for (int i = 0; i < widthSize; i++) {
            for (int j = 0; j < heightSize; j++) {
                Rectangle tile = new Rectangle(TILE_SIZE, TILE_SIZE);
                tile.setFill(getTileResource(grid[i][j]));

                GridPane.setRowIndex(tile, i);
                GridPane.setColumnIndex(tile, j);

                gridPane.getChildren().add(tile);
            }
        }
    }

    private void readMap(String fileName){

        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader("src/main/resources/maps/" + fileName));
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

    private Color paintBlock(int id){
        return switch (id) {
            case 0 -> Color.web("#1d6630");//grass
            case 1 -> Color.web("#606060FF");//stone
            case 2 -> Color.web("#543222FF");//dirt
            case 3 -> Color.web("#247abf"); //water
            case 4 -> Color.web("#cfc482");//sand
            default -> Color.web("#1d6630");//grass default
        };
    }

    private void changeTileResource(){

    }

    public void highlightTile(double x, double y){
        getTileAtPosition(x, y);
    }

    public int getTileAtPosition(double x, double y) {
        double mapOffset = widthSize * heightSize;

        int gridX = (int)Math.floor((y + mapOffset) / TILE_SIZE);
        int gridY = (int)Math.floor((x + mapOffset) / TILE_SIZE);

        if (gridX < 0 || gridX >= widthSize || gridY < 0 || gridY >= heightSize) {
            return -1; // Out of bounds
        }

        //System.out.println(grid[gridX][gridY]);

        return grid[gridX][gridY]; // Use Row-major order
    }

    public int getWidthSize() {
        return widthSize;
    }

    public int getHeightSize() {
        return heightSize;
    }

    public int getTILE_SIZE() {
        return TILE_SIZE;
    }
}
