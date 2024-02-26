package org.example.zombiegame;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Map {

    private int widthSize;
    private int heightSize;
    private int[][] grid;
    private final String mapFileName = "map.txt";

    private GridPane gridPane;

    public Map(GridPane gridPane){
        this.gridPane = gridPane;

        readMap(mapFileName);
        paintMap();
    }

    private void paintMap() {
        int tileSize = 50;

        for (int i = 0; i < widthSize; i++) {
            for (int j = 0; j < heightSize; j++) {
                Rectangle tile = new Rectangle(tileSize, tileSize);
                tile.setFill(paintBlock(grid[i][j]));

                GridPane.setRowIndex(tile, i);
                GridPane.setColumnIndex(tile, j);

                gridPane.getChildren().add(tile);
            }
        }
    }

    /*
    Reads the map file
    First 2 numbers on the map file are width and height
    The numbers under are the matricies for the map
    Initializes widthSize, heightSize, and grid[][]
     */
    public void readMap(String fileName){

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

    /*
    To test if matrix is made properly
     */
    private void printMatrix(){
        for(int i = 0; i < widthSize; i++){
            for(int j = 0; j < heightSize; j++){
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }

    public int getWidthSize() {
        return widthSize;
    }

    public int getHeightSize() {
        return heightSize;
    }

    public void setHeightSize(int heightSize) {
        this.heightSize = heightSize;
    }

    public void setWidthSize(int widthSize) {
        this.widthSize = widthSize;
    }
}