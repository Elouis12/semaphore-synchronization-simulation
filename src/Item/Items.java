package Item;

/*

    Parent class for items


*/

import java.util.Random;

public class Items {

    protected final Status status; // a status object to track status of each item
    protected final String item; // the item type



    protected int quantity;

    public Items(String type){

        Random random = new Random();

        this.status = new Status();
        this.item = type;
//        this.quantity = random.nextInt( 4 - 1 ) + 1;
    }

    public Items(String type, int quantity){

        this.status = new Status();
        this.item = type;
        this.quantity = quantity;
    }

    public String getItem() {
        return this.item;
    }

    public void updateItem(){

        // after every lap, the level will deplete based on the rate
        // if it is the case that after that lap the item is fully depleted,
        // it will access the supply coordinator to replenish
        this.status.updateItem();

    }

    public int getLevel() {

        return this.status.getLevel();
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {

        this.quantity = quantity;
    }

    public void restartLevel() {

        this.status.restartLevel();
    }
}
