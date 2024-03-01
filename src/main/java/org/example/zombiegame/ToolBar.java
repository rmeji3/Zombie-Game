package org.example.zombiegame;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ToolBar {

    class Item{
        private PlacableObject item;

        private int count;

        private int inventorySlot;

        private boolean hasItem;
        public Item(){
            this.item = new PlacableObject();
            this.count = 0;
        }
        public Item(PlacableObject item, int count, int inventorySlot){
            this.item = item;
            this.count = count;
            this.inventorySlot = inventorySlot;
            this.hasItem = false;
        }

        public int getCount() {
            return count;
        }
        public void setCount(int count){
            this.count = count;
        }
        public int getInventorySlot(){
            return inventorySlot;
        }

        public PlacableObject getItem(){
            return this.item;
        }

    }
    private HashMap<Integer,Item> itemList;


    private GridPane inventory;
    private StackPane inventoryWrapper;

     int currInvSlot = -1;

    private StackPane root;
    public ToolBar(StackPane root){
        this.root = root;
        inventory = new GridPane();
        itemList = new HashMap<>();


        for(int i = 0; i < 9; i++){
            Rectangle slot = new Rectangle(50, 50);
            inventory.add(slot, i, 0);
            slot.setStroke(Color.GREY);
            slot.setStrokeWidth(2);
            slot.setFill(Color.web("#62626296"));
            clickHandler(slot);

        }

        inventoryWrapper = new StackPane(inventory);
        inventoryWrapper.setMaxSize(450, 50); // Set to the desired size of the inventory
        root.getChildren().add(inventoryWrapper);
        StackPane.setAlignment(inventoryWrapper, Pos.BOTTOM_CENTER);

        inventory.setStyle("-fx-background-color: #F3F3F300;");
//        addToSlot((Rectangle) inventory.getChildren().get(2),"A:\\main desktop\\vs code projects\\ZombieGame\\src\\main\\resources\\objects\\trees\\tree.png");

    }

    private void switchSlot(Rectangle slot){

        slot.setStroke(Color.BLACK);
    }

    public void resetInventory(){
        for(Node child : inventory.getChildren()){
            if(child instanceof Rectangle s){
                s.setStroke(Color.GREY);
            }
        }

    }
    public void buttonHandler(KeyEvent event){
        Rectangle currSlot = null;
       if(event.getCode() == KeyCode.DIGIT1){
           resetInventory();
           currSlot = (Rectangle)inventory.getChildren().get(0);
           currSlot.setStroke(Color.BLACK);
           currInvSlot = 0;
       }
       if(event.getCode() == KeyCode.DIGIT2){
           resetInventory();
            currSlot = (Rectangle)inventory.getChildren().get(1);
           currSlot.setStroke(Color.BLACK);
           currInvSlot = 1;
       }
       if(event.getCode() == KeyCode.DIGIT3){
           resetInventory();
            currSlot = (Rectangle)inventory.getChildren().get(2);
           currSlot.setStroke(Color.BLACK);
           currInvSlot = 2;
       }
       if(event.getCode() == KeyCode.DIGIT4){
           resetInventory();
            currSlot = (Rectangle)inventory.getChildren().get(3);
           currSlot.setStroke(Color.BLACK);
           currInvSlot = 3;
       }
       if(event.getCode() == KeyCode.DIGIT5){
           resetInventory();
            currSlot = (Rectangle)inventory.getChildren().get(4);
           currSlot.setStroke(Color.BLACK);
           currInvSlot = 4;
       }
        if(event.getCode() == KeyCode.DIGIT6){
            resetInventory();
             currSlot = (Rectangle)inventory.getChildren().get(5);
            currSlot.setStroke(Color.BLACK);
            currInvSlot = 5;
        }
        if(event.getCode() == KeyCode.DIGIT7){
            resetInventory();
             currSlot = (Rectangle)inventory.getChildren().get(6);
            currSlot.setStroke(Color.BLACK);
            currInvSlot = 6;
        }
        if(event.getCode() == KeyCode.DIGIT8){
            resetInventory();
             currSlot = (Rectangle)inventory.getChildren().get(7);
            currSlot.setStroke(Color.BLACK);
            currInvSlot = 7;
        }
        if(event.getCode() == KeyCode.DIGIT9){
            resetInventory();
            currSlot = (Rectangle)inventory.getChildren().get(8);
            currSlot.setStroke(Color.BLACK);
            currInvSlot = 8;
        }

        if(currSlot != null)
            switchSlot(currSlot);
    }
    public boolean checkItemCount(PlacableObject item){
        removeFromInventory(item);
        if(!inInventory(item)){
            return false;
        }

        decreaseItemCount(item);
        return true;
    }
    private boolean inInventory(PlacableObject item){
        return itemList.containsKey(item.getObjectID());
    }
    private void decreaseItemCount(PlacableObject item){
        int itemID = item.getObjectID();
        int itemCount = itemList.get(itemID).getCount();
        itemList.get(itemID).setCount(itemCount-1);
    }
    private void clickHandler(Rectangle slot){
        slot.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
               resetInventory();
               switchSlot(slot);
            }
        });
    }

    public int getItemCount(PlacableObject item){
        return itemList.get(item.getObjectID()).getCount();
    }
    public void addToInventory(PlacableObject item, int count, int slot){
        int itemID = item.getObjectID();


//        System.out.println(itemID);
        if(itemList.containsKey(itemID)){
            itemList.get(itemID).setCount(count);
        }else{
            itemList.put(itemID, new Item(item,count, slot));
        }
        addIconToSlot(slot,item);
    }
    private void removeFromInventory(PlacableObject item){
        int itemID = item.getObjectID();

        if(inInventory(item) && itemList.get(itemID).getCount() == 0){
            int itemSlot = itemList.get(itemID).getInventorySlot();

            itemList.remove(itemID);
            removeIconFromSlot(itemSlot);
        }

    }
    private void addIconToSlot(int slot, PlacableObject item){
        Rectangle currSlot = (Rectangle)inventory.getChildren().get(slot);
        currSlot.setFill(item.getImagePattern());

    }
    private void removeIconFromSlot(int itemSlot){
        inventory.getChildren().remove(itemSlot);
        Rectangle r = new Rectangle(50,50);
        r.setStroke(Color.GREY);
        r.setStrokeWidth(2);
        r.setFill(Color.web("#62626296"));
        inventory.add(r,itemSlot,0);
    }

    public int getCurrentItem(){


        for(Map.Entry<Integer, Item> s : itemList.entrySet()){
            int invSlot = s.getValue().getInventorySlot();
            int id = s.getValue().getItem().getObjectID();
            System.out.println("comparing:"+invSlot + " with:" + currInvSlot);
            if(currInvSlot == invSlot)
                return id;
        }
        return -1;
    }


}
