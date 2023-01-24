package Vehicle;

import Item.Battery;
import Item.Drink;
import Item.Items;
import Item.Tire;

import java.util.concurrent.Semaphore;

public class AutoMobile extends Vehicle{

    public AutoMobile(Semaphore semaphore, SupplyCoordinator supplyCoordinator, long seedNumber) {


        super("\uD83D\uDE97 (automobile)", semaphore, supplyCoordinator, seedNumber );
    }

}
