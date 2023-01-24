package Vehicle;

import Item.Battery;
import Item.Drink;
import Item.Items;
import Item.Tire;

import java.util.concurrent.Semaphore;

public class RacingCar extends Vehicle {

    public RacingCar(Semaphore semaphore, SupplyCoordinator supplyCoordinator, long seedNumber) {

        super("\uD83C\uDFCE (racing car)", semaphore, supplyCoordinator, seedNumber);
    }

}
