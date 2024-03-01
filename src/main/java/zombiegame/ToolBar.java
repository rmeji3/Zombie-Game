package zombiegame;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import zombiegame.entities.Entity;

import java.util.ArrayList;
import java.util.Objects;

public class ToolBar {

    class ItemSlot {
        private Entity item;

        private String name  = "Item";

        private int count;

        private int inventorySlot;

        public ItemSlot(){
            this.item = new Entity();
            this.count = 0;
        }
        public ItemSlot(Entity item, int count, int inventorySlot){
            this.item = item;
            this.count = count;
            this.inventorySlot = inventorySlot;
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

        public String getName(){return item.getItemName();}


        public Entity getItem(){
            return this.item;
        }

    }
    private ArrayList<ItemSlot> itemSlotList;

    private GridPane inventory;
    private StackPane inventoryWrapper;


    int currInvSlot = 0;

    private StackPane root;


    public ToolBar(StackPane root){
        this.root = root;

        inventory = new GridPane();
        itemSlotList = new ArrayList<>(9);

        initToolBar();
        for(int i = 0; i < 9; i++){
            Rectangle slot = new Rectangle(50, 50);
            inventory.add(slot, i, 0);
            slot.setStroke(Color.GREY);
            slot.setStrokeWidth(2);
            slot.setFill(Color.web("#62626296"));
//            clickHandler(slot);

        }

        inventoryWrapper = new StackPane(inventory);
        inventoryWrapper.setMaxSize(450, 50); // Set to the desired size of the inventory
        root.getChildren().add(inventoryWrapper);
        StackPane.setAlignment(inventoryWrapper, Pos.BOTTOM_CENTER);

        inventory.setStyle("-fx-background-color: #F3F3F300;");
//        addToSlot((Rectangle) inventory.getChildren().get(2),"A:\\main desktop\\vs code projects\\ZombieGame\\src\\main\\resources\\objects\\trees\\tree.png");

    }

    private void initToolBar(){
        for(int i = 0; i < 9; i++){
            itemSlotList.add(i,null);
        }
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
    public boolean checkItemCount(Entity item){
        removeFromInventory(item);
        if(!inInventory(item)){
            System.out.println("not in inventory");
            return false;
        }

        decreaseItemCount(item);
        return true;
    }
    private boolean inInventory(Entity item){
        for(ItemSlot i : itemSlotList){
            if( i != null && Objects.equals(i.getName(), item.getItemName())){
                return true;
            }
        }

        return false;
    }
    private void decreaseItemCount(Entity item){
        int itemID = item.getObjectID();
        int itemCount = itemSlotList.get(itemID).getCount();
        itemSlotList.get(itemID).setCount(itemCount-1);
    }

    public int getItemCount(Entity item){
        return itemSlotList.get(item.getObjectID()).getCount();
    }

    private int getItemIndex(Entity item){
        int index = 0;
        for(ItemSlot i : itemSlotList){
            if(i != null && i.getItem() == item){
                return index;
            }
            index++;
        }
        return -1;
    }
    private int getNextFreeSlot(){
        int index = 0;
        for(ItemSlot i : itemSlotList){
            if (i == null) {
                System.out.println("Next free index: " + index);
                return index;
            }
            index++;
        }
        return -1;
    }

    public void addToToolBar(Entity item, int count){
        int index = getItemIndex(item);
        if(inInventory(item)){
            itemSlotList.get(index).setCount(count);
        }else{
            index = getNextFreeSlot();
            if(index != -1)
            {
                itemSlotList.set(index,new ItemSlot(item,count, index));
                addIconToSlot(index,item);
            }
        }
    }
    public void printToolBar(){

        for(ItemSlot i : itemSlotList){
            if(i != null)
                System.out.println(i.getName());
            else
                System.out.println(i);
        }
    }

    private void removeFromInventory(Entity item){
        int itemID = item.getObjectID();

        if(inInventory(item) && itemSlotList.get(itemID).getCount() == 0){
            int itemSlot = itemSlotList.get(itemID).getInventorySlot();

            itemSlotList.remove(itemID);
            removeIconFromSlot(itemSlot);
        }

    }
    private void addIconToSlot(int slot, Entity item){
        Rectangle currSlot = (Rectangle)inventory.getChildren().get(slot);
        currSlot.setFill(item.getImagePattern());

    }
    private void removeIconFromSlot(int itemSlot){

        Rectangle r = (Rectangle) inventory.getChildren().get(itemSlot);
        r.setStroke(Color.GREY);
        r.setStrokeWidth(2);
        r.setFill(Color.web("#62626296"));
    }

    public int getCurrentItem(){

        if(itemSlotList.get(currInvSlot) == null){

            return -1;
        }
        else{
            return itemSlotList.get(currInvSlot).getItem().getObjectID();
        }
    }


}
