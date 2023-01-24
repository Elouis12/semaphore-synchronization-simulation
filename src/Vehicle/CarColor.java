package Vehicle;

import java.util.HashSet;
import java.util.Set;

public class CarColor {

    private final Set<String> colorsChosen; // make sure duplicates not chosen

    private final String[] carColors = {
            /*"\u001B[30m",*/
            "\u001B[31m",
            "\u001B[32m",
            "\u001B[33m",
            "\u001B[34m",
            "\u001B[35m",
            "\u001B[36m",
            "\u001B[37m",
            "\u001B[90m",
            "\u001B[91m",
            "\u001B[92m",
            "\u001B[93m",
            "\u001B[94m",
            "\u001B[95m",
            "\u001B[96m",
            "\u001B[97m",
    };

    private final String ANSI_RESET = "\u001B[0m";

    public CarColor(){

        this.colorsChosen = new HashSet<>();
    }

    public String getColor(){

        String carColor = carColors[ (int) (Math.random() * carColors.length ) ];

        // chose a different color if already chosen
        while( this.colorsChosen.contains( carColor + "." ) ){

            carColor = carColors[ (int) (Math.random() * carColors.length ) ];

        }

        this.colorsChosen.add( carColor + "." );

        return carColor;
    }

    public String resetColor(){

        return this.ANSI_RESET;
    }

}
