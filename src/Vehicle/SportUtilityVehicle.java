package Vehicle;

import Item.Battery;
import Item.Drink;
import Item.Items;
import Item.Tire;

import java.util.concurrent.Semaphore;

public class SportUtilityVehicle extends Vehicle{

    public SportUtilityVehicle(Semaphore semaphore, SupplyCoordinator supplyCoordinator, long seedNumber) {


        super("\uD83D\uDE8E (trolley bus)"/*"\uD83D\uDE99"*/, semaphore, supplyCoordinator, seedNumber);
    }

}
