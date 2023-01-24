package Item;

/*

    gets the status of the item
*/

public class Status {

    int level; // current health status of item
    int depletionRate; // how quickly the item will deplete

    public Status(){

        this.level = 100;
        this.depletionRate = (int) (Math.random() * 25 ) + 1;
    }

    public void updateItem(){

        // after every lap, the level will deplete based on the rate
        // if it is the case that after that lap the item is fully depleted,
        // it will access the supply coordinator to replenish

        this.level -= this.depletionRate;
    }

    // GETS CURRENT HEALTH STATUS OF ITEM
    public int getLevel() {

        return this.level;
    }

    // FILLS UP THE ITEM'S STATUS
    public void restartLevel() {

        this.level = 100;
    }
}
