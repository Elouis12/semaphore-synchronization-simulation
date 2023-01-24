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

public class SupplyCoordinator implements Runnable {


    private final List<Items> items;// to choose from 2 of the 2 items randomly
                                   // each value is paired with a corresponding item

    private Semaphore semaphore;
    private Random random;
    private int carsOnTrack;
    private int carLimit;

    public SupplyCoordinator(Semaphore semaphore, int carsOnTrack, long seedValue){

        this.items = new ArrayList<>();
        this.semaphore = semaphore;
        this.random = new Random();
        random.setSeed(seedValue-20);

        this.carsOnTrack = carsOnTrack;
        this.carLimit = 6;

    }

    // returns 2 uniquely generated item from the 3 necessary items
    public List<Items> loadItems(){


        Battery battery = new Battery(random.nextInt(5)+5 );
        Tire tire = new Tire( random.nextInt(5)+5 );
        Drink drink = new Drink( random.nextInt(5)+5 );
        this.items.addAll( Arrays.asList( battery, tire, drink ) );

        List<Items> generatedItems = new ArrayList<>();

        // random index of items to get
        int itemNumber1 =  random.nextInt( items.size() );
        int itemNumber2 =  random.nextInt( items.size() );


        // in the event it would generate duplicate items
        while( ( items.get(itemNumber2).getItem().equalsIgnoreCase( items.get(itemNumber1).getItem() )) ){

            itemNumber2 = (int) ( Math.random() * this.items.size() );
        }

        generatedItems.addAll( Arrays.asList( items.get( itemNumber1 ), items.get( itemNumber2 ) ) );

        return generatedItems;

    }

    @Override
    public void run() {


        try{

            if( carsOnTrack >= carLimit ){

                semaphore.acquire();

            }else{

                semaphore.release();
            }

        }catch (Exception e){

            e.printStackTrace();
        }
    }
}
