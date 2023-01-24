import Item.Battery;
import Item.Drink;
import Item.Items;
import Item.Tire;
import Vehicle.*;

import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class RaceTrack {

    // create the 6 cars of 3 types and start the threads
    public static void race(long seedNumber) throws InterruptedException {

        Semaphore semaphore = new Semaphore(1); // semaphore for supply coordinator
        Semaphore carSemaphore = new Semaphore(6); // only allow 6 cars on the lap at a time

        // supply coordinator
        SupplyCoordinator supplyCoordinator = new SupplyCoordinator(carSemaphore, 6, seedNumber-7);
        Thread supplyCoordinatorThread = new Thread( supplyCoordinator, "1" );
        supplyCoordinatorThread.start();

        // items in infinite value a car can have
        Items[] items = { new Battery(Integer.MAX_VALUE), new Tire(Integer.MAX_VALUE), new Drink(Integer.MAX_VALUE) };

        // the 6 cars

        // car 1
        Vehicle car1 = new SportUtilityVehicle(semaphore, supplyCoordinator, seedNumber - 1);
        car1.getBattery().setQuantity(Integer.MAX_VALUE);
        Thread car1Thread = new Thread(car1, "1" );
        car1Thread.start();

        // car 2
        Vehicle car2 = new RacingCar(semaphore, supplyCoordinator, seedNumber - 2);
        car2.getBattery().setQuantity(Integer.MAX_VALUE);
        Thread car2Thread = new Thread(car2, "2" );
        car2Thread.start();

        // car 3
        Vehicle car3 = new SportUtilityVehicle(semaphore, supplyCoordinator, seedNumber - 3);
        car3.getTire().setQuantity(Integer.MAX_VALUE);
        Thread car3Thread = new Thread(car3, "3" );
        car3Thread.start();

        // car 4
        Vehicle car4 = new RacingCar(semaphore, supplyCoordinator, seedNumber - 4);
        car4.getTire().setQuantity(Integer.MAX_VALUE);
        Thread car4Thread = new Thread(car4, "4" );
        car4Thread.start();

        // car 5
        Vehicle car5 = new RacingCar(semaphore, supplyCoordinator, seedNumber - 5);
        car5.getDrink().setQuantity(Integer.MAX_VALUE);
        Thread car5Thread = new Thread(car5, "5" );
        car5Thread.start();

        // car 6
        Vehicle car6 = new AutoMobile(semaphore, supplyCoordinator, seedNumber - 6);
        car6.getDrink().setQuantity(Integer.MAX_VALUE);
        Thread car6Thread = new Thread(car6, "6" );
        car6Thread.start();

    }

    public static void main(String[] args) throws InterruptedException {

        long seedNumber = Long.parseLong( args[0] );
        System.out.println("SEED VALUE " + seedNumber);

        Scanner input = new Scanner(System.in);
        System.out.println( "\u001B[33m" + "TO STOP THE RACE, PRESS ENTER" + "\u001B[0m" + "\n" );

        try {

            race(/*12345*/seedNumber);

            System.out.println("");

            input.nextLine();

            String enterKey = input.nextLine();

            // allows the user to stop the program
            while( enterKey.isEmpty() ){

                System.out.println("RACE OVER");
                System.exit(0);
            }

        }catch (IllegalArgumentException e){

            e.printStackTrace();
        }

    }
}

