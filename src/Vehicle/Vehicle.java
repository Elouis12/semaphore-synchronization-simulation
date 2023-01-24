package Vehicle;

import Item.Battery;
import Item.Drink;
import Item.Items;
import Item.Tire;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Vehicle implements Runnable {


    protected Battery battery;
    protected Tire tire;
    protected Drink drink;
    protected int laps; // total laps ran randomly from 1 - 10
    protected int lapsBeforeReplenishing; // the number of laps to run before replenishing
    protected String carType;
    protected Semaphore semaphore;
    protected SupplyCoordinator supplyCoordinator;
    protected int replenishTime; // in milli seconds
    protected CarColor carColors;
    protected List<Items> items;

    private long seedNumber;

    private Random random;


// CONSTRUCTORS
    public Vehicle(String carType, Semaphore semaphore, SupplyCoordinator supplyCoordinator, long seedNumber) {

        this( new Battery(), new Tire(), new Drink(), carType, semaphore, supplyCoordinator, seedNumber);
    }

    public Vehicle(Battery battery, Tire tire, Drink drink, String carType, Semaphore semaphore, SupplyCoordinator supplyCoordinator, long seedNumber) {


       this.random = new Random();
       random.setSeed(seedNumber);

        this.items = new ArrayList<>();

        this.setBattery(battery);
        this.setTire(tire);
        this.setDrink(drink);
        this.setCarType(carType);
        this.setSemaphore(semaphore);
        this.supplyCoordinator = supplyCoordinator;
        this.setLaps( random.nextInt(10) + 1 ) ; // assign a random lap from 1 - 10

        this.replenishTime = random.nextInt(2500 - 1000 ) + 1000; // between 1 - 2.5 seconds
        this.lapsBeforeReplenishing = random.nextInt( this.laps ) + 1 ; // what lap to replenish items

        while( this.lapsBeforeReplenishing > this.replenishTime ){

            this.lapsBeforeReplenishing = random.nextInt( this.laps - 1 ) + 1 ; // what lap to replenish items

        }
        this.carColors = new CarColor();

    }


// GETTERS AND SETTERS

    public void setMaxItem(Items item){

        if( item instanceof Battery ){

            this.setBattery( (Battery) item );

        }else if( item instanceof Tire ){

            this.setTire( (Tire) item );

        }else if( item instanceof Drink ){

            this.setDrink( (Drink) item );
        }

    }
    public int getLapsBeforeReplenishing() {
        return lapsBeforeReplenishing;
    }

    public void setLapsBeforeReplenishing(int lapsBeforeReplenishing) {
        this.lapsBeforeReplenishing = lapsBeforeReplenishing;
    }

    public Semaphore getSemaphore() {
        return semaphore;
    }
    public void setSemaphore(Semaphore semaphore) {

        this.semaphore = semaphore;
    }

    public SupplyCoordinator getSupplyCoordinator() {
        return supplyCoordinator;
    }

    public int getReplenishTime() {
        return replenishTime;
    }

    public void setReplenishTime(int replenishTime) {
        this.replenishTime = replenishTime;
    }

    public CarColor getCarColors() {
        return carColors;
    }

    public void setCarColors( CarColor carColors) {
        this.carColors = carColors;
    }

    // getters and setters to replenish and check status of each item
    public Battery getBattery() {
        return this.battery;
    }

    public void setBattery(Battery battery) {

        this.items.remove(this.battery); // remove teh current item
        this.battery = battery;
        this.items.add(battery);
    }

    public Tire getTire() {
        return this.tire;
    }

    public void setTire(Tire tire) {

        this.items.remove(this.tire); // remove teh current item
        this.tire = tire;
        this.items.add(this.tire);

    }

    public Drink getDrink() {
        return this.drink;
    }

    public void setDrink(Drink drink) {

        this.items.remove(this.drink); // remove teh current item
        this.drink = drink;
        this.items.add(this.drink);
    }

    public int getLaps() {
        return this.laps;
    }

    public void setLaps(int laps) {
        this.laps = laps;
    }

    public void replenish(Items item) {

        if( item instanceof Battery ){

            this.setBattery( (Battery) item);

        }

        if( item instanceof Tire ){

            this.setTire( (Tire) item);

        }

        if( item instanceof Drink ){

            this.setDrink( (Drink) item);

        }

    }

    public String getCarType() {

        return this.carType;
    }

    public void setCarType(String carType) {

        this.carType = carType;
    }


    public boolean hasNeededItems(List<Items> supplyItems) {

        for (Items item : supplyItems) {

            // check if not the infinite item
            for (int x = 0; x < items.size(); x++) {

                if (
                        (
                            items.get(x).getQuantity() == Integer.MAX_VALUE &&
                            items.get(x).getItem().equalsIgnoreCase( item.getItem() )
                        )
                ) {

                    return false;
                }
            }
        }

        return true;
    }
        @Override
        public void run () {

            try {

                String carNumber = "#" + Thread.currentThread().getName();
                String carColor = carColors.getColor();

                // run around the track (infinitely)
                // remove equal sign and make sure in the random it make laps before replenish lap -1 so t will never be laps = lapsbeforereplenish
                // also make sure that lap is never > laps before replenish
                while (
                        this.getLaps() >= this.lapsBeforeReplenishing
                                ||
                                (this.getBattery().getQuantity() >= 1 ||
                                        this.getTire().getQuantity() >= 4 ||
                                        this.getDrink().getQuantity() >= 1
                                )

                ) {

                    // prints out information about each car
                    System.out.println(carColor + carNumber + carColors.resetColor() + " " +
                            this.getCarType() +
                            " { LAP: " + this.getLaps() + " | REPLENISH LAP: " + this.lapsBeforeReplenishing + " } " +
                            "[ " + this.getBattery().getItem() + " " + this.getBattery().getQuantity() + " | " +
                            this.getTire().getItem() + " " + this.getTire().getQuantity() + " | " +
                            this.getDrink().getItem() + " " + this.getDrink().getQuantity() + " ]"

                    );


                    this.setLaps(this.getLaps() - 1); // subtract from laps


                    // loop through items and subtract one form the ones not in infinte amount
                    for (Items item : this.items) {

                        if (item.getQuantity() != Integer.MAX_VALUE) { // subtract from battery if not in infinite

                            item.setQuantity(item.getQuantity() - 1);
                        }
                    }


                    Thread.sleep(1000);

                    // if time to replenish
                    if (
                            this.getLaps() <= this.lapsBeforeReplenishing
                                    ||
                                    (
                                            this.getBattery().getQuantity() <= 0 || // battery finished
                                                    this.getTire().getQuantity() <= 3 || // tire finished
                                                    this.getDrink().getQuantity() <= 0 // drinks finished
                                    )

                    ) {

                        // acquire method to get one permit
                        semaphore.acquire();

                        // the time it takes to replenish
                        Thread.sleep(replenishTime);

                        // replenish an item
                    List<Items> supplyItems = this.supplyCoordinator.loadItems();


                        while ( !hasNeededItems(supplyItems) ) {

                            supplyItems = this.supplyCoordinator.loadItems();
                        }


                        // grab the items it needs
                        for( Items supplyItem : supplyItems ){

                            // grab what it need
                            if( this.getBattery().getQuantity() <= 1 && supplyItem instanceof Battery ){

                                this.replenish(supplyItem);
                            }
                            if( this.getTire().getQuantity() <= 3 && supplyItem instanceof Tire ){

                                this.replenish(supplyItem);
                            }
                            if( this.getDrink().getQuantity() <= 1 && supplyItem instanceof Drink ){

                                this.replenish(supplyItem);
                            }
                        }

                        // set new laps and replenish lap
                        this.laps = random.nextInt(10) + 1; // assign a random lap from 1 - 10
                        this.lapsBeforeReplenishing = random.nextInt(this.laps) + 1; // what lap to replenish items

                        while (this.lapsBeforeReplenishing > this.replenishTime) {

                            this.lapsBeforeReplenishing = random.nextInt(this.laps - 1) + 1; // what lap to replenish items

                        }

                    System.out.println( "\n" + carColor + carNumber + carColors.resetColor() + " " + this.getCarType() + " " + "\u001B[37m" + "\u001B[105m" + "replenished" + carColors.resetColor() + " @ "  + ( this.replenishTime / 1000d ) + "s " + supplyItems.get(0).getItem() + " | " + supplyItems.get(1).getItem() + " { NEW LAP: " + this.getLaps() + " | REPLENISH LAP: " + this.lapsBeforeReplenishing + " }\n");

                        // releasing permit
                        semaphore.release();

                    }

                }

            } catch (Exception e) {

                e.printStackTrace();
            }

        }
    }

